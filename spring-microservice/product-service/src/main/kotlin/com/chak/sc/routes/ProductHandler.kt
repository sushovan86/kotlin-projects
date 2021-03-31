package com.chak.sc.routes

import com.chak.sc.messages.InvalidInventoryId
import com.chak.sc.messages.ProductIdShouldBeInteger
import com.chak.sc.messages.ProductIdShouldBePositive
import com.chak.sc.service.InventoryMapper
import com.chak.sc.service.ProductMapper
import com.chak.sc.service.ProductService
import com.chak.sc.utils.DomainErrors
import com.chak.sc.utils.returnSingleResponse
import com.github.michaelbull.result.*
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

internal data class ProductInput(val id: Int, val includeInventories: Boolean)

class ProductHandler(
    private val productService: ProductService,
    private val productMapper: ProductMapper,
    private val inventoryMapper: InventoryMapper
) {

    suspend fun findById(serverRequest: ServerRequest): ServerResponse =
        validateProductId(serverRequest)
            .andThen { productInput ->
                productService.findProductById(
                    productInput.id,
                    productInput.includeInventories
                )
            }
            .map { product -> productMapper.toProductDTO(product) }
            .returnSingleResponse(serverRequest)

    suspend fun findProductWithStatusById(serverRequest: ServerRequest): ServerResponse =
        validateProductId(serverRequest)
            .andThen { productInput -> productService.findProductWithStatusById(productInput.id) }
            .returnSingleResponse(serverRequest)

    suspend fun findByInventoryId(serverRequest: ServerRequest) =
        validateInventoryId(serverRequest)
            .andThen { id -> productService.findByInventoryId(id) }
            .map { inventory -> inventoryMapper.toInventoryDTO(inventory) }
            .returnSingleResponse(serverRequest)

    private fun validateProductId(serverRequest: ServerRequest): Result<ProductInput, DomainErrors> {

        val idPathValue: String = serverRequest.pathVariable("id")

        val id: Int? = idPathValue.toIntOrNull()
        val includeInventory: Boolean = serverRequest.queryParam("includeInventories")
            .map(String::toBoolean)
            .orElse(false)

        return when {
            id == null -> Err(ProductIdShouldBeInteger(idPathValue))
            id <= 0 -> Err(ProductIdShouldBePositive(id))
            else -> Ok(ProductInput(id, includeInventory))
        }
    }

    private fun validateInventoryId(serverRequest: ServerRequest): Result<Int, DomainErrors> {

        val idPathValue: String = serverRequest.pathVariable("id")
        val id: Int? = idPathValue.toIntOrNull()

        return when {
            id == null || id <= 0 -> Err(InvalidInventoryId(idPathValue))
            else -> Ok(id)
        }
    }
}