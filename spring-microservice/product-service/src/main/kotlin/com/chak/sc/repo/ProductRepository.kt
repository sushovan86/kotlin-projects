package com.chak.sc.repo

import com.chak.sc.entity.Product
import com.chak.sc.model.ProductWithStatusDTO
import com.chak.sc.repo.queries.PRODUCT_STATUS
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface ProductRepository : CoroutineCrudRepository<Product, Int> {

    @Query(PRODUCT_STATUS)
    suspend fun getProductAvailabilityStatusById(productId:Int) : ProductWithStatusDTO?

}