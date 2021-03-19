package com.chak.sc.routes

import com.chak.sc.entity.Customer
import com.chak.sc.messages.*
import com.chak.sc.model.CustomerDTO
import com.chak.sc.service.CustomerMapper
import com.chak.sc.service.CustomerService
import com.github.michaelbull.result.*
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

class CustomerHandler(
    private val customerService: CustomerService,
    private val customerMapper: CustomerMapper
) {

    suspend fun findCustomerById(serverRequest: ServerRequest): ServerResponse =
        validateCustomerId(serverRequest.pathVariable("id"))
            .andThen { findById(it) }
            .andThen { convertToDTO(it) }
            .returnSingleResponse()

    private suspend fun findById(id: Int): Result<Customer, DomainErrors> =
        runCatching { customerService.getCustomerById(id) }
            .mapError { ServerError(it) }
            .andThen {
                when (it) {
                    null -> Err(CustomerNotFound(id))
                    else -> Ok(it)
                }
            }

    private fun convertToDTO(customer: Customer): Result<CustomerDTO, DomainErrors> =
        runCatching { customerMapper.toCustomerDTO(customer) }
            .mapError { ServerError(it) }


    private fun validateCustomerId(idPathValue: String): Result<Int, DomainErrors> {

        val id = idPathValue.toIntOrNull()

        return when {
            id == null -> Err(CustomerIdShouldBeInteger(idPathValue))
            id <= 0 -> Err(CustomerIdShouldBePositive(id))
            else -> Ok(id)
        }
    }
}