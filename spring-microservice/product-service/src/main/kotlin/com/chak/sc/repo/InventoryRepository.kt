package com.chak.sc.repo

import com.chak.sc.entity.Inventory
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface InventoryCrudRepository : CoroutineCrudRepository<Inventory, Int> {

    suspend fun findByProductId(productId: Int): List<Inventory>

}