package com.chak.sc.repo

import com.chak.sc.entity.Customer
import kotlinx.coroutines.flow.Flow
import org.springframework.cglib.core.Local
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.core.flow
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import org.springframework.data.relational.core.query.isEqual
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.flow
import org.springframework.stereotype.Repository
import java.time.LocalDate

interface CustomerCrudRepository : CoroutineCrudRepository<Customer, Int> {

    suspend fun findByCustomerNumber(customerNumber: String): Customer?

    fun findAllBySurname(surname: String): Flow<Customer>
}


@Repository
class CustomerRepository(
    private val databaseClient: DatabaseClient
) {

    fun findCustomersByAge(age: Int, comparator: String?): Flow<Customer> {

        val criteria: String = when (comparator) {
            "older" -> "extract(year from age(c.dob)) > :age"
            "younger" -> "extract(year from age(c.dob)) < :age"
            else -> "extract(year from age(c.dob)) = :age"
        }

        return databaseClient.sql("select * from customer c where $criteria")
            .bind("age", age)
            .map { row ->
                Customer(
                    id = row.get("id", Integer::class.java) as? Int ?: -1,
                    customerNumber = row.get("customerNumber", String::class.java) ?: "",
                    firstName = row.get("firstName", String::class.java),
                    surname = row.get("surname", String::class.java),
                    userName = row.get("userName", String::class.java),
                    dob = row.get("dob", LocalDate::class.java)
                )
            }
            .flow()
    }

}