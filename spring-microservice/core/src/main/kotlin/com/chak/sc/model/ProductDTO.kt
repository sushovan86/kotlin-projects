package com.chak.sc.model

import java.time.LocalDate

data class ProductDTO(
    val id: Int? = null,
    val productDescription: String?,
    val company: String,
    val price: Double = 0.0,
    val inventoryList: List<InventoryDTO>? = null
)

data class InventoryDTO(
    val id: Int? = null,
    val inventoryCode: String,
    val dateWhenAdded: LocalDate,
    val status: String,
    val product: ProductDTO? = null
)

data class ProductWithStatusDTO(
    val id: Int,
    val productDescription: String?,
    val company: String,
    val price: Double,
    val soldCount: Double,
    val availableCount: Double,
    val returnedCount: Double
)

