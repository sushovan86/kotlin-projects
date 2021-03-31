package com.chak.sc.repo

import com.chak.sc.entity.Order
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface OrderRepository : CoroutineCrudRepository<Order, Int> {

    fun findAllByCustomerId(customerId: Int): Flow<Order>
}

