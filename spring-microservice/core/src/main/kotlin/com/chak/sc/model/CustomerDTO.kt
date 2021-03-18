package com.chak.sc.model

import java.time.LocalDate

data class CustomerDTO(
    val id: Int? = null,
    val customerNumber: String,
    val firstName: String? = null,
    val surname: String? = null,
    val userName: String? = null,
    val dataOfBirth: LocalDate? = null,
    val addresses: List<AddressDTO>? = null
)

data class AddressDTO(
    val id: Int? = null,
    val addressType: String,
    val addressLine1: String? = null,
    val addressLine2: String? = null,
    val addressLine3: String? = null,
    val state: String? = null,
    val postCode: String,
    val country: String
)
