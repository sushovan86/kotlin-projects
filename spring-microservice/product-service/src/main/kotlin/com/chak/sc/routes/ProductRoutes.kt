package com.chak.sc.routes

import com.chak.sc.ProductServiceApplication
import com.chak.sc.utils.getLogger
import com.chak.sc.utils.loggingFilter
import org.springframework.web.reactive.function.server.coRouter

val logger = getLogger<ProductServiceApplication>()

fun productRoutes(productHandler: ProductHandler) = coRouter {

    "/products".nest {

        GET("findById/{id}", productHandler::findById)
        GET("findByIdWithStatus/{id}", productHandler::findProductWithStatusById)
    }

    loggingFilter(logger)
}