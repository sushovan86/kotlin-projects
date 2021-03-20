package com.chak.sc.routes

import org.springframework.web.reactive.function.server.coRouter

fun applicationRouter(customerHandler: CustomerHandler) =
    coRouter {

        "/customers".nest {

            GET("findById/{id}", customerHandler::findCustomerById)
        }
    }