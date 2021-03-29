package com.chak.sc.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table
data class Product(
    @Id var id: Int? = null,
    @Column("productdescription") val productDescription: String?,
    val company: String,
    val price: Double
) {
    @Transient
    var inventoryList: List<Inventory>? = null
}

@Table("productinventory")
data class Inventory(
    @Id var id: Int? = null,
    @Column("inventorycode") val inventoryCode: String,
    @Column("datewhenadded") val dateWhenAdded: LocalDate,
    val status: String,
    @Column("productid") val productId: Int
)