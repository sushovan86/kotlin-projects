package com.chak.sc.utils

import com.chak.sc.model.ErrorDTO
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapBoth
import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.ok
import java.util.*

interface DomainErrors {
    suspend fun response(serverRequest: ServerRequest): ServerResponse
}

fun errorAttributesFrom(serverRequest: ServerRequest?, message: String) =
    ErrorDTO(
        timestamp = Date(),
        path = serverRequest?.path() ?: "",
        requestId = serverRequest?.exchange()?.request?.id ?: "",
        message = message
    ).toMap()

data class ErrorResponse(
    val status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    val message: String = ""
) : DomainErrors {

    override suspend fun response(serverRequest: ServerRequest): ServerResponse =
        ServerResponse
            .status(status)
            .bodyValueAndAwait(
                ErrorDTO(
                    timestamp = Date(),
                    path = serverRequest.path(),
                    requestId = serverRequest.exchange().request.id,
                    message = message
                )
            )
}

suspend inline fun <T> Result<T, DomainErrors>.returnSingleResponse(serverRequest: ServerRequest): ServerResponse =
    this.mapBoth(
        { success ->
            when (success) {
                null -> ErrorResponse(HttpStatus.NO_CONTENT, "No Data").response(serverRequest)
                else -> ok().bodyValueAndAwait(success)
            }
        },
        { error -> error.response(serverRequest) }
    )

suspend inline fun <reified T : Any> Result<Flow<T>, DomainErrors>.returnFlowResponse(serverRequest: ServerRequest)
        : ServerResponse =
    this.mapBoth(
        { success ->
            when (MediaType.TEXT_EVENT_STREAM) {
                in serverRequest.headers().accept(),
                serverRequest.headers().contentType().orElse(null) -> ok().sse().bodyAndAwait(success)
                else -> ok().json().bodyAndAwait(success)
            }
        },
        { error -> error.response(serverRequest) }
    )