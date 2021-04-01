package com.chak.sc.service

import com.chak.sc.entity.Customer
import com.chak.sc.messages.CustomerNotFound
import com.chak.sc.model.OrderDTO
import com.chak.sc.repo.AddressRepository
import com.chak.sc.repo.CustomerCrudRepository
import com.chak.sc.repo.CustomerRepository
import com.chak.sc.utils.DomainErrors
import com.chak.sc.utils.getLogger
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.toResultOr
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.bodyToFlow

@Service
class CustomerService(
    private val customerCrudRepository: CustomerCrudRepository,
    private val customerRepository: CustomerRepository,
    private val addressRepository: AddressRepository,
    private val webClient: WebClient
) {

    val logger = getLogger<CustomerService>()

    fun getCustomersByAge(age: Int, comparator: String?) =
        customerRepository.findCustomersByAge(age, comparator)

    suspend fun getCustomerDetails(customerNumber: String): Result<Customer, DomainErrors> =
        customerCrudRepository.findByCustomerNumber(customerNumber)
            ?.also {
                populateAddressDetails(it)
            }.toResultOr {
                CustomerNotFound(customerNumber)
            }

    suspend fun getCustomerById(id: Int): Result<Customer, DomainErrors> =
        customerCrudRepository.findById(id)
            ?.also {
                populateAddressDetails(it)
            }.toResultOr {
                CustomerNotFound(id.toString())
            }

    suspend fun getOrdersForCustomer(id: Int): Result<Flow<OrderDTO>, DomainErrors> {

        logger.debug("Customer Id: $id")

        val orderFlow: Flow<OrderDTO> = webClient.get()
            .uri("/orders/findByCustomerId/{id}", id)
            .accept(MediaType.TEXT_EVENT_STREAM)
            .retrieve()
            .bodyToFlow<OrderDTO>()
            .onEach {
                it.inventory = webClient.get()
                    .uri("/inventories/findById/{id}", it.inventoryId)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .awaitBody()
            }

        return Ok(orderFlow)
    }

    private suspend fun populateAddressDetails(customer: Customer) {
        customer.addressList = customer.id?.let {
            addressRepository.findAllByCustomerId(it)
        }
    }
}