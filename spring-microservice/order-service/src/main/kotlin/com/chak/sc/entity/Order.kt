package com.chak.sc.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table("order_placed")
data class Order(
    @Id
    var id: Int? = null,
    val inventoryId: Int,
    val customerId: Int,
    val orderDate: LocalDate,
    val status: String,
    val returnedDate: LocalDate?
)