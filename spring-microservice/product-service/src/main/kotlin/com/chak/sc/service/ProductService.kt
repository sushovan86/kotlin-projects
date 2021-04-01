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
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.toResultOr
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val inventoryCrudRepository: InventoryCrudRepository,
    private val inventoryRepository: InventoryRepository
) {

    suspend fun findProductById(id: Int, includeInventories: Boolean = false): Result<Product, DomainErrors> {

        val product = productRepository.findById(id)

        product?.takeIf { prod -> includeInventories && prod.id != null }
            ?.apply {
                inventoryList = inventoryCrudRepository.findByProductId(id)
            }

        return product.toResultOr { ProductNotFoundForID(id) }
    }

    suspend fun findProductWithStatusById(id: Int): Result<ProductWithStatusDTO, DomainErrors> =
        productRepository.getProductAvailabilityStatusById(id)
            .toResultOr { ProductNotFoundForID(id) }

    suspend fun findByInventoryId(id: Int): Result<Inventory, DomainErrors> =
        inventoryRepository.findByInventoryId(id)
            .toResultOr { InventoryNotFound(id) }
}