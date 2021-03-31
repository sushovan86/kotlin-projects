package com.chak.sc.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table("order_placed")
data class Order(
    @Id var id: Int? = null,
    @Column("inventoryid") val inventoryId: Int,
    @Column("customerid") val customerId: Int,
    @Column("ordereddate") val orderDate: LocalDate,
    val status: String,
    @Column("returndate") val returnedDate: LocalDate?
)