package com.chak.sc.routes

import com.chak.sc.CustomerServiceApplication
import com.chak.sc.utils.getLogger
import com.chak.sc.utils.loggingFilter
import org.springframework.web.reactive.function.server.coRouter

val logger = getLogger<CustomerServiceApplication>()

fun applicationRouter(customerHandler: CustomerHandler) =
    coRouter {

        "/customers".nest {

            GET("findById/{id}", customerHandler::findCustomerById)
            GET("findByNo/{number}", customerHandler::findCustomerById)
            GET("age/{age}", customerHandler::findCustomersByAge)
            GET("order/{id}", customerHandler::findOrdersForCustomer)
        }

        loggingFilter(logger)
    }




