package com.chak.sc.messages

import com.chak.sc.utils.DomainErrors
import com.chak.sc.utils.ErrorResponse
import org.springframework.http.HttpStatus

class ProductNotFoundForID(private val id: Int) :
    DomainErrors by ErrorResponse(
        status = HttpStatus.BAD_REQUEST,
        message = "No product found against product id '$id'"
    )

class ProductIdShouldBeInteger(private val idPathVar: String?) :
    DomainErrors by ErrorResponse(
        status = HttpStatus.BAD_REQUEST,
        message = "Product Id '$idPathVar' must be integer"
    )

class ProductIdShouldBePositive(private val id: Int) :
    DomainErrors by ErrorResponse(
        status = HttpStatus.BAD_REQUEST,
        message = "Customer Id '$id' must be > 0"
    )

class InventoryNotFound(private val id: Int) :
    DomainErrors by ErrorResponse(
        status = HttpStatus.BAD_REQUEST,
        message = "No product found against product id '$id'"
    )

class InvalidInventoryId(private val id: String) :
    DomainErrors by ErrorResponse(
        status = HttpStatus.BAD_REQUEST,
        message = "Invalid inventory id '$id'"
    )