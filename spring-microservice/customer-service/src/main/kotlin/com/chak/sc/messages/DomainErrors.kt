package com.chak.sc.messages

import com.chak.sc.utils.DomainErrors
import com.chak.sc.utils.ErrorResponse
import org.springframework.http.HttpStatus

class CustomerNotFound(private val id: String) :
    DomainErrors by ErrorResponse(
        status = HttpStatus.OK,
        message = "No customer found against '$id'"
    )

class AgeIsInvalid(private val age: String?) :
        DomainErrors by ErrorResponse(
            status = HttpStatus.BAD_REQUEST,
            message = "Age $age provided is invalid"
        )
