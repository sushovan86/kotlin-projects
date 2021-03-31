package com.chak.sc.service

import com.chak.sc.entity.Inventory
import com.chak.sc.entity.Product
import com.chak.sc.model.InventoryDTO
import com.chak.sc.model.ProductDTO
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Qualifier

@Qualifier
annotation class WithoutInventory

@Qualifier
annotation class WithoutInventoryDTO

@Qualifier
annotation class WithoutProduct

@Qualifier
annotation class WithoutProductDTO

@Mapper(componentModel = "spring", uses = [ProductMapper::class])
interface InventoryMapper {

    @Mapping(source = "inventory.product", target = "product", qualifiedBy = [WithoutInventoryDTO::class])
    fun toInventoryDTO(inventory: Inventory): InventoryDTO

    @WithoutProductDTO
    @Mapping(target = "product", ignore = true)
    fun toInventoryDTOWithoutProduct(inventory: Inventory): InventoryDTO

    @Mapping(source = "inventoryDTO.product", target = "product", qualifiedBy = [WithoutInventory::class])
    fun toInventory(inventoryDTO: InventoryDTO): Inventory

    @WithoutProduct
    @Mapping(target = "product", ignore = true)
    fun toInventoryWithoutProduct(inventoryDTO: InventoryDTO): Inventory
}

@Mapper(componentModel = "spring", uses = [InventoryMapper::class])
interface ProductMapper {

    @Mapping(source = "product.inventoryList", target = "inventoryList", qualifiedBy = [WithoutProductDTO::class])
    fun toProductDTO(product: Product): ProductDTO

    @WithoutInventoryDTO
    @Mapping(target = "inventoryList", ignore = true)
    fun toProductDTOWithoutInventory(product: Product): ProductDTO

    @Mapping(source = "productDTO.inventoryList", target = "inventoryList", qualifiedBy = [WithoutProduct::class])
    fun toProduct(productDTO: ProductDTO): Product

    @WithoutInventory
    @Mapping(target = "inventoryList", ignore = true)
    fun toProductWithoutInventory(productDTO: ProductDTO): Product
}