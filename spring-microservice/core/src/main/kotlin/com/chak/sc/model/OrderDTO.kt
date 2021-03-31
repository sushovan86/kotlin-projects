package com.chak.sc.model

import java.time.LocalDate

data class OrderDTO(
    val id: Int,
    val inventoryId: Int,
    val customerId: Int,
    val orderDate: LocalDate,
    val status: String,
    val returnedDate: LocalDate?
) {

    var inventory : InventoryDTO? = null
}
