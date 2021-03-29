package com.chak.sc.service

import com.chak.sc.entity.Inventory
import com.chak.sc.entity.Product
import com.chak.sc.messages.ProductNotFoundForID
import com.chak.sc.model.ProductWithStatusDTO
import com.chak.sc.repo.InventoryCrudRepository
import com.chak.sc.repo.ProductRepository
import com.chak.sc.utils.DomainErrors
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val inventoryCrudRepository: InventoryCrudRepository
) {

    suspend fun findProductById(id: Int): Result<Product, DomainErrors> {

        val product: Product = productRepository.findById(id) ?: return Err(ProductNotFoundForID(id))
        val inventoryList: List<Inventory>? = product.id?.let {
            inventoryCrudRepository.findByProductId(it)
        }
        product.inventoryList = inventoryList
        return Ok(product)
    }

    suspend fun findProductWithStatusById(id: Int): Result<ProductWithStatusDTO, DomainErrors> =
        productRepository.getProductAvailabilityStatusById(id)
            ?.let {
                Ok(it)
            } ?: Err(ProductNotFoundForID(id))

}