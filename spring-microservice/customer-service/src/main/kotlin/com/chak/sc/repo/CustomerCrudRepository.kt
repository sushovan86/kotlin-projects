package com.chak.sc.repo

import com.chak.sc.entity.Customer
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.convert.R2dbcConverter
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.flow
import org.springframework.stereotype.Repository

interface CustomerCrudRepository : CoroutineCrudRepository<Customer, Int> {

    suspend fun findByCustomerNumber(customerNumber: String): Customer?
}

@Repository
class CustomerRepository(
    private val databaseClient: DatabaseClient,
    private val r2dbcConverter: R2dbcConverter
) {

    fun findCustomersByAge(age: Int, comparator: String?): Flow<Customer> {

        val criteria: String = when (comparator) {
            "older" -> "extract(year from age(c.dob)) > :age"
            "younger" -> "extract(year from age(c.dob)) < :age"
            else -> "extract(year from age(c.dob)) = :age"
        }

        return databaseClient.sql("select * from customer c where $criteria")
            .bind("age", age)
            .map { row -> r2dbcConverter.read(Customer::class.java, row) }
            .flow()
    }

}