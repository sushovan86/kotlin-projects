package com.chak.sc.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.reactive.function.server.CoRouterFunctionDsl

inline fun <reified T> getLogger(): Logger = LoggerFactory.getLogger(T::class.java)

fun CoRouterFunctionDsl.loggingFilter(logger: Logger) {
    filter { serverRequest, responseHandler ->

//        val request = serverRequest.aw

        responseHandler(serverRequest)
    }
}