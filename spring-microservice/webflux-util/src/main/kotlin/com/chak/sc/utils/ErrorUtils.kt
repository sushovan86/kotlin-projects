package com.chak.sc.utils

import com.chak.sc.model.ErrorDTO
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.*
import java.util.*

interface DomainErrors {
    suspend fun response(serverRequest: ServerRequest): ServerResponse
}

fun errorDTOFrom(serverRequest: ServerRequest?, message: String): ErrorDTO =
    ErrorDTO(
        path = serverRequest?.path() ?: "",
        requestId = serverRequest?.exchange()?.request?.id ?: "",
        message = message
    )

fun errorAttributesFrom(serverRequest: ServerRequest?, message: String): MutableMap<String, Any> =
    errorDTOFrom(serverRequest, message).toMap()

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

object NoDataAvailable : DomainErrors by ErrorResponse(HttpStatus.NO_CONTENT)
