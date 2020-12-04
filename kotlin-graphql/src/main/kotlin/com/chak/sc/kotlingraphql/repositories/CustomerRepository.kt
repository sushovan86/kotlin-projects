package com.chak.sc.kotlingraphql.repositories

import com.chak.sc.kotlingraphql.model.Address
import com.chak.sc.kotlingraphql.model.Customer
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface CustomerRepository : CoroutineCrudRepository<Customer, Long>

interface AddressRepository : CoroutineCrudRepository<Address, Long> {

    fun findByCustomerId(id: Long): Flow<Address>
}