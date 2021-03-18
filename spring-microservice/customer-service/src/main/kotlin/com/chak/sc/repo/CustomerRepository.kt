package com.chak.sc.repo

import com.chak.sc.entity.Customer
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface CustomerRepository : CoroutineCrudRepository<Customer, Int> {

    suspend fun findByCustomerNumber(customerNumber: String): Customer?

    fun findAllBySurname(surname: String): Flow<Customer>
}