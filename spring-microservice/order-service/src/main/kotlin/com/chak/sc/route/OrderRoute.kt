package com.chak.sc.route

import com.chak.sc.OrderServiceApplication
import com.chak.sc.utils.getLogger
import com.chak.sc.utils.loggingFilter
import org.springframework.web.reactive.function.server.coRouter

val logger = getLogger<OrderServiceApplication>()

fun orderRoutes(orderHandler: OrderHandler) = coRouter {

    "/orders".nest {

        GET("findByCustomerId/{id}", orderHandler::findOrdersForCustomer)
    }

    loggingFilter(logger)
}