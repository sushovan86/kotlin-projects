package com.chak.sc.route

import com.chak.sc.messages.CustomerIdShouldBeInteger
import com.chak.sc.messages.CustomerIdShouldBePositive
import com.chak.sc.service.OrderMapper
import com.chak.sc.service.OrderService
import com.chak.sc.utils.DomainErrors
import com.chak.sc.utils.returnFlowResponse
import com.github.michaelbull.result.*
import kotlinx.coroutines.flow.mapNotNull
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

class OrderHandler(
    private val orderService: OrderService,
    private val orderMapper: OrderMapper
) {

    suspend fun findOrdersForCustomer(serverRequest: ServerRequest): ServerResponse =
        validateCustomerId(serverRequest.pathVariable("id"))
            .andThen { id -> orderService.findOrdersByCustomerId(id) }
            .map { orderFlow -> orderFlow.mapNotNull { orderMapper.toOrderDTO(it) } }
            .returnFlowResponse(serverRequest)


    private fun validateCustomerId(idPathValue: String): Result<Int, DomainErrors> {

        val id = idPathValue.toIntOrNull()
        return when {
            id == null -> Err(CustomerIdShouldBeInteger(idPathValue))
            id <= 0 -> Err(CustomerIdShouldBePositive(id))
            else -> Ok(id)
        }
    }

}