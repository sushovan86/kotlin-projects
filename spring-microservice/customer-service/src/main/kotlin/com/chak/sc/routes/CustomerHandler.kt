package com.chak.sc.routes

import com.chak.sc.messages.AgeIsInvalid
import com.chak.sc.messages.CustomerIdShouldBeInteger
import com.chak.sc.messages.CustomerIdShouldBePositive
import com.chak.sc.service.CustomerMapper
import com.chak.sc.service.CustomerService
import com.chak.sc.utils.DomainErrors
import com.chak.sc.utils.returnFlowResponse
import com.chak.sc.utils.returnSingleResponse
import com.github.michaelbull.result.*
import kotlinx.coroutines.flow.map
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

class CustomerHandler(
    private val customerService: CustomerService,
    private val customerMapper: CustomerMapper
) {

    suspend fun findCustomersByAge(serverRequest: ServerRequest): ServerResponse =
        extractAgeAndOperator(serverRequest)
            .map {
                val (age, operator) = it
                customerService
                    .getCustomersByAge(age, operator)
                    .map { customer ->
                        customerMapper.toCustomerDTO(customer)
                    }
            }
            .returnFlowResponse(serverRequest)

    suspend fun findCustomerById(serverRequest: ServerRequest): ServerResponse =
        validateCustomerId(serverRequest.pathVariable("id"))
            .andThen { id -> customerService.getCustomerById(id) }
            .map { customer -> customerMapper.toCustomerDTO(customer) }
            .returnSingleResponse(serverRequest)

    private fun extractAgeAndOperator(serverRequest: ServerRequest): Result<Pair<Int, String?>, DomainErrors> {

        val inputAge = serverRequest.pathVariable("age")
        val age = inputAge.toIntOrNull() ?: return Err(AgeIsInvalid(inputAge))
        val comparator: String? = serverRequest.queryParam("operator")
            .orElse(null)

        return Ok(age to comparator)
    }

    private fun validateCustomerId(idPathValue: String): Result<Int, DomainErrors> {

        val id = idPathValue.toIntOrNull()
        return when {
            id == null -> Err(CustomerIdShouldBeInteger(idPathValue))
            id <= 0 -> Err(CustomerIdShouldBePositive(id))
            else -> Ok(id)
        }
    }
}
