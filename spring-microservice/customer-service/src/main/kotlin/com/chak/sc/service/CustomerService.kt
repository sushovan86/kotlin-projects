package com.chak.sc.service

import com.chak.sc.entity.Customer
import com.chak.sc.repo.AddressRepository
import com.chak.sc.repo.CustomerRepository
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val addressRepository: AddressRepository
) {

    suspend fun getCustomerDetails(customerNumber: String): Customer? {

        val customer: Customer? = customerRepository.findByCustomerNumber(customerNumber);

        return customer?.also {
            populateAddressDetails(customer)
        }
    }

    suspend fun getCustomerById(id: Int): Customer? {

        val customer: Customer? = customerRepository.findById(id)

        return customer?.also {
            populateAddressDetails(customer)
        }
    }

    private suspend fun populateAddressDetails(customer: Customer): Unit {

        customer.id?.let {
            addressRepository.findAllByCustomerId(it)
        }.also {
            customer.addressList = it
        }
    }

}