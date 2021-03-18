package com.chak.sc.model

import java.time.LocalDate

data class CustomerDTO(
    val id: Int? = null,
    val customerNumber: String,
    val firstName: String?,
    val surname: String?,
    val userName: String?,
    val dataOfBirth: LocalDate?,
    val addresses: List<AddressDTO> = mutableListOf()
)

data class AddressDTO(
    val id: Int? = null,
    val addressType: String,
    val addressLine1: String?,
    val addressLine2: String?,
    val addressLine3: String?,
    val state: String?,
    val postCode: String,
    val country: String
)
