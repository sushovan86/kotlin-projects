package com.chak.sc.messages

import com.chak.sc.utils.DomainErrors
import com.chak.sc.utils.ErrorResponse
import org.springframework.http.HttpStatus

class CustomerIdShouldBeInteger(private val idPathVar: String?) :
    DomainErrors by ErrorResponse(
        status = HttpStatus.BAD_REQUEST,
        message = "Customer Id '$idPathVar' must be integer"
    )

class CustomerIdShouldBePositive(private val id: Int) :
    DomainErrors by ErrorResponse(
        status = HttpStatus.BAD_REQUEST,
        message = "Customer Id '$id' must be > 0"
    )

class CustomerNotFound(private val id: String) :
    DomainErrors by ErrorResponse(
        status = HttpStatus.NOT_FOUND,
        message = "No customer found against '$id'"
    )

class AgeIsInvalid(private val age: String?) :
        DomainErrors by ErrorResponse(
            status = HttpStatus.BAD_REQUEST,
            message = "Age $age provided is invalid"
        )
