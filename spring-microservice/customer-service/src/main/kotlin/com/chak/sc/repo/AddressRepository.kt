package com.chak.sc.repo

import com.chak.sc.entity.Address
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface AddressRepository : CoroutineCrudRepository<Address, Int> {

    suspend fun findAllByCustomerId(customerId: Int): List<Address>
}