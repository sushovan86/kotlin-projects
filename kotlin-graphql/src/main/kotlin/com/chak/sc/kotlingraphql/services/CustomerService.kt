package com.chak.sc.kotlingraphql.services

import com.chak.sc.kotlingraphql.graphql.model.AddressGraphType
import com.chak.sc.kotlingraphql.graphql.model.CustomerGraph
import com.chak.sc.kotlingraphql.repositories.AddressRepository
import com.chak.sc.kotlingraphql.repositories.CustomerRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val addressRepository: AddressRepository
) {

    suspend fun findCustomerById(id: Long) = customerRepository.findById(id)?.let {

        val customer = CustomerGraph(
            it.id,
            it.customerNumber,
            it.firstName,
            it.surname,
            it.userName,
            it.dob
        )
        customer.addressPopulator = { customerGraph ->
            findCustomerAddresses(customerGraph)
        }
        customer
    }

    suspend fun findCustomerAddresses(customerGraph: CustomerGraph) = addressRepository
        .findByCustomerId(customerGraph.id!!)
        .map {
            AddressGraphType(
                id = it.id,
                addressType = it.addressType,
                addressLine1 = it.addressLine1,
                addressLine2 = it.addressLine2,
                addressLine3 = it.addressLine3,
                state = it.state,
                postcode = it.postCode,
                country = it.country,
                customer = customerGraph
            )
        }
        .toList()
}