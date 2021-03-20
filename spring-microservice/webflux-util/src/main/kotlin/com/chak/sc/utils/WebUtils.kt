package com.chak.sc.utils

import com.chak.sc.model.ErrorDTO
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapBoth
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

interface DomainErrors {
    suspend fun response(): ServerResponse
}

data class ErrorResponse(
    val status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    val message: String = ""
) : DomainErrors {

    override suspend fun response(): ServerResponse = ServerResponse
        .status(status)
        .bodyValueAndAwait(ErrorDTO(message))
}

suspend inline fun <T> Result<T, DomainErrors>.returnSingleResponse(): ServerResponse =
    this.mapBoth(
        { success ->
            when (success) {
                null -> ErrorResponse(HttpStatus.NO_CONTENT, "No Data").response()
                else -> ServerResponse.ok().bodyValueAndAwait(success)
            }
        },
        { error -> error.response() }
    )

