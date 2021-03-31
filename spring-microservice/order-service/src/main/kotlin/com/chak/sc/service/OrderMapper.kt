package com.chak.sc.service

import com.chak.sc.entity.Order
import com.chak.sc.model.OrderDTO
import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface OrderMapper {

    fun toOrderDTO(order: Order) : OrderDTO

    @InheritInverseConfiguration
    fun toOrder(orderDTO: OrderDTO) : Order

}