package com.chak.sc.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.reactive.function.server.CoRouterFunctionDsl
import org.springframework.web.reactive.function.server.contentTypeOrNull

inline fun <reified T> getLogger(): Logger = LoggerFactory.getLogger(T::class.java)

fun CoRouterFunctionDsl.loggingFilter(logger: Logger) {
    filter { serverRequest, responseHandler ->

        logger.debug("""---
            Request Id: ${serverRequest.exchange().request.id}
            Query Params: ${serverRequest.queryParams()}
            Request Path: ${serverRequest.requestPath()}
            Accept-Type: ${serverRequest.headers().accept()}
            Content-Type: ${serverRequest.headers().contentTypeOrNull()}
        """.trimIndent())

        responseHandler(serverRequest)
    }
}