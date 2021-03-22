package com.chak.sc.utils

import com.chak.sc.model.ErrorDTO
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapBoth
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
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
                else -> ServerResponse.ok().bodyValueAndAwait(success)
            }
        },
        { error -> error.response(serverRequest) }
    )

