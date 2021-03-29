package com.chak.sc.routes

import com.chak.sc.messages.ProductIdShouldBeInteger
import com.chak.sc.messages.ProductIdShouldBePositive
import com.chak.sc.messages.ProductMapper
import com.chak.sc.service.ProductService
import com.chak.sc.utils.DomainErrors
import com.chak.sc.utils.returnSingleResponse
import com.github.michaelbull.result.*
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse


class ProductHandler(
    private val productService: ProductService,
    private val productMapper: ProductMapper
) {

    suspend fun findById(serverRequest: ServerRequest): ServerResponse =
        validateProductId(serverRequest.pathVariable("id"))
            .andThen { id -> productService.findProductById(id) }
            .map { product -> productMapper.toProductDTO(product) }
            .returnSingleResponse(serverRequest)

    suspend fun findProductWithStatusById(serverRequest: ServerRequest): ServerResponse =
        validateProductId(serverRequest.pathVariable("id"))
            .andThen { id -> productService.findProductWithStatusById(id) }
            .returnSingleResponse(serverRequest)

    private fun validateProductId(idPathValue: String): Result<Int, DomainErrors> {

        val id = idPathValue.toIntOrNull()
        return when {
            id == null -> Err(ProductIdShouldBeInteger(idPathValue))
            id <= 0 -> Err(ProductIdShouldBePositive(id))
            else -> Ok(id)
        }
    }

}