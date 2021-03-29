package com.chak.sc.messages

import com.chak.sc.entity.Inventory
import com.chak.sc.entity.Product
import com.chak.sc.model.InventoryDTO
import com.chak.sc.model.ProductDTO
import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface InventoryMapper {

    fun toInventoryDTO(inventory: Inventory): InventoryDTO

    @InheritInverseConfiguration
    fun toInventory(inventoryDTO: InventoryDTO): Inventory
}

@Mapper(componentModel = "spring", uses = [InventoryMapper::class])
interface ProductMapper {

    @Mapping(source = "product.inventoryList", target = "inventoryList")
    fun toProductDTO(product: Product): ProductDTO

    @InheritInverseConfiguration
    fun toProduct(productDTO: ProductDTO): Product
}