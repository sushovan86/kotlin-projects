package com.chak.sc.service

import com.chak.sc.entity.Order
import com.chak.sc.repo.OrderRepository
import com.chak.sc.utils.DomainErrors
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class OrderService(private val orderRepository: OrderRepository) {

    fun findOrdersByCustomerId(customerId: Int): Result<Flow<Order>, DomainErrors> =
        Ok(orderRepository.findAllByCustomerId(customerId))

}