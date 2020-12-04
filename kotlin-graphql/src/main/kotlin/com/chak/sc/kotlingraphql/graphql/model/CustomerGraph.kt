package com.chak.sc.kotlingraphql.graphql.model

import com.expediagroup.graphql.annotations.GraphQLIgnore
import com.expediagroup.graphql.annotations.GraphQLName
import java.time.LocalDate

@GraphQLName("Customer")
data class CustomerGraph(
    val id: Long? = null,
    val customerNumber: String,
    val firstname: String?,
    val surname: String,
    val username: String,
    val dob: LocalDate
) {

    @GraphQLIgnore
    internal var addressPopulator: (suspend (CustomerGraph) -> List<AddressGraphType>)? = null

    suspend fun addresses() = addressPopulator?.let {
        it(this)
    }
}

@GraphQLName("Address")
data class AddressGraphType(
    val id: Long? = null,
    val addressType: String,
    val addressLine1: String?,
    val addressLine2: String?,
    val addressLine3: String?,
    val state: String,
    val postcode: String,
    val country: String,
    var customer: CustomerGraph? = null
)

