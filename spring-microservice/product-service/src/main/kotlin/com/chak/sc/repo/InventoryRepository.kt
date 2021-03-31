package com.chak.sc.repo

import com.chak.sc.entity.Inventory
import com.chak.sc.entity.Product
import com.chak.sc.repo.queries.INVENTORY_DETAILS
import com.chak.sc.utils.read
import org.springframework.data.r2dbc.convert.R2dbcConverter
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitSingleOrNull
import org.springframework.stereotype.Repository

interface InventoryCrudRepository : CoroutineCrudRepository<Inventory, Int> {

    suspend fun findByProductId(productId: Int): List<Inventory>
}

@Repository
class InventoryRepository(
    private val databaseClient: DatabaseClient,
    private val r2dbcConverter: R2dbcConverter
) {

    suspend fun findByInventoryId(inventoryId: Int): Inventory? = databaseClient.sql(INVENTORY_DETAILS)
        .bind("inventoryId", inventoryId)
        .map { row, rowMetadata ->
            r2dbcConverter.read(Inventory::class.java, row, rowMetadata)
                .also {
                    it.product = r2dbcConverter.read<Product>(row, "prod_")
                }
        }
        .awaitSingleOrNull()

}