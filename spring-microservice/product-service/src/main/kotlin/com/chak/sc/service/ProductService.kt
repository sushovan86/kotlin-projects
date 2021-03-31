package com.chak.sc.service

import com.chak.sc.entity.Inventory
import com.chak.sc.entity.Product
import com.chak.sc.messages.InventoryNotFound
import com.chak.sc.messages.ProductNotFoundForID
import com.chak.sc.model.ProductWithStatusDTO
import com.chak.sc.repo.InventoryCrudRepository
import com.chak.sc.repo.InventoryRepository
import com.chak.sc.repo.ProductRepository
import com.chak.sc.utils.DomainErrors
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val inventoryCrudRepository: InventoryCrudRepository,
    private val inventoryRepository: InventoryRepository
) {

    suspend fun findProductById(id: Int, includeInventories: Boolean = false): Result<Product, DomainErrors> {

        val product: Product = productRepository.findById(id) ?: return Err(ProductNotFoundForID(id))
        product.inventoryList = when {
            includeInventories && product.id != null -> inventoryCrudRepository.findByProductId(product.id!!)
            else -> null
        }
        return Ok(product)
    }

    suspend fun findProductWithStatusById(id: Int): Result<ProductWithStatusDTO, DomainErrors> =
        productRepository.getProductAvailabilityStatusById(id)
            ?.let {
                Ok(it)
            } ?: Err(ProductNotFoundForID(id))

    suspend fun findByInventoryId(id: Int): Result<Inventory, DomainErrors> =
        inventoryRepository.findByInventoryId(id)
            ?.let {
                Ok(it)
            } ?: Err(InventoryNotFound(id))

}