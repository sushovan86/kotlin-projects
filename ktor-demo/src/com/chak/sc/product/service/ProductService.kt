package com.chak.sc.com.chak.sc.product.service

import com.chak.sc.com.chak.sc.product.repo.Product
import com.chak.sc.com.chak.sc.product.repo.Products
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class ProductService {

    fun getProductById(id: Int?) = transaction {
        Products.select {
            Products.id eq id
        }
            .map {
                it[Products.productDescription]
            }
    }
}