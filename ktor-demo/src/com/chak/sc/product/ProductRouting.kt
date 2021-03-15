package com.chak.sc.product

import com.chak.sc.com.chak.sc.product.service.ProductService
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.productRouting() {

    val productService: ProductService by inject()
    routing {

        get("/product/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            call.respond(productService.getProductById(id))
        }
    }

}