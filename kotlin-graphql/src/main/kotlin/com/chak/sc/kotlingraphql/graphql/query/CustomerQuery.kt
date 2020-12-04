package com.chak.sc.kotlingraphql.graphql.query

import com.chak.sc.kotlingraphql.graphql.model.CustomerGraph
import com.chak.sc.kotlingraphql.services.CustomerService
import com.expediagroup.graphql.annotations.GraphQLDescription
import com.expediagroup.graphql.scalars.ID
import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component

@Component
class CustomerQuery(private val customerService: CustomerService) : Query {

    @GraphQLDescription("Fetch customer by id")
    suspend fun getCustomerByID(id: Long) = customerService.findCustomerById(id)
}