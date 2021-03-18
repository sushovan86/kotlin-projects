package com.chak.sc.routes

import com.chak.sc.service.CustomerMapper
import com.chak.sc.service.CustomerService
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.badRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyValueAndAwait

class CustomerHandler(
    private val customerService: CustomerService,
    private val customerMapper: CustomerMapper
) {

    suspend fun findCustomerById(serverRequest: ServerRequest): ServerResponse {
        val customer = serverRequest.pathVariable("id").let {
            customerService.getCustomerById(it.toInt())
        }

        return customer?.let {
            ok().bodyValueAndAwait(customerMapper.toCustomerDTO(it))
        } ?: badRequest().bodyValueAndAwait("Customer not present");
    }
}