package com.chak.sc.utils

import com.chak.sc.model.ErrorDTO
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapEither
import com.github.michaelbull.result.merge
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

interface DomainErrors {
    suspend fun response(): ServerResponse
}

suspend inline fun <T> Result<T, DomainErrors>.returnSingleResponse(): ServerResponse =
    this.mapEither(
        { success ->
            when (success) {
                null -> ServerResponse
                    .status(HttpStatus.NO_CONTENT)
                    .bodyValueAndAwait(ErrorDTO("No Data"))

                else -> ServerResponse.ok().bodyValueAndAwait(success)
            }
        },
        { error -> error.response() }
    ).merge()
