package com.chak.sc.messages

import com.chak.sc.utils.DomainErrors
import com.chak.sc.utils.ErrorResponse
import org.springframework.http.HttpStatus

class CustomerIdShouldBeInteger(private val idPathVar: String?) :
    DomainErrors by ErrorResponse(HttpStatus.BAD_REQUEST, "Customer Id '$idPathVar' must be integer")

class CustomerIdShouldBePositive(private val id: Int) :
    DomainErrors by ErrorResponse(HttpStatus.BAD_REQUEST, "Customer Id '$id' must be > 0")

class CustomerNotFound(private val id: String) :
    DomainErrors by ErrorResponse(HttpStatus.NOT_FOUND, "No customer found against customer id '$id'")

class ServerError(private val throwable: Throwable) :
    DomainErrors by ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, throwable.localizedMessage)
