package com.chak.sc.service

import com.chak.sc.repo.OrderRepository
import com.github.michaelbull.result.Ok
import org.springframework.stereotype.Service

@Service
class OrderService(private val orderRepository: OrderRepository) {

    fun findOrdersByCustomerId(customerId: Int) =
        Ok(orderRepository.findAllByCustomerId(customerId))

}