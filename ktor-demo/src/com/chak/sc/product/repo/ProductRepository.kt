package com.chak.sc.com.chak.sc.product.repo

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Products : IntIdTable("product") {
    val productDescription = varchar("productdescription", 100)
    val company = varchar("company", 100)
    val price = double("price")
}

class Product(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<Product>(Products)

    val productDesc by Products.productDescription
    val company by Products.company
    val price by Products.price

    override fun toString() = "Product($id,$productDesc,$company,$price)"
}

