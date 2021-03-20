package com.chak.sc.service

import com.chak.sc.entity.Customer
import com.chak.sc.messages.CustomerNotFound
import com.chak.sc.repo.AddressRepository
import com.chak.sc.repo.CustomerRepository
import com.chak.sc.utils.DomainErrors
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.toResultOr
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val addressRepository: AddressRepository
) {

    suspend fun getCustomerDetails(customerNumber: String): Result<Customer, DomainErrors> =
        customerRepository.findByCustomerNumber(customerNumber)
            ?.also {
                populateAddressDetails(it)
            }.toResultOr {
                CustomerNotFound(customerNumber)
            }


    suspend fun getCustomerById(id: Int): Result<Customer, DomainErrors> =
        customerRepository.findById(id)
            ?.also {
                populateAddressDetails(it)
            }.toResultOr {
                CustomerNotFound(id.toString())
            }


    private suspend fun populateAddressDetails(customer: Customer): Unit {
        customer.id
            ?.let {
                addressRepository.findAllByCustomerId(it)
            }?.also {
                customer.addressList = it
            }
    }
}