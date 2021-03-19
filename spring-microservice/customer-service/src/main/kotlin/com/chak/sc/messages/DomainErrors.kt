package com.chak.sc.messages

import com.chak.sc.model.ErrorDTO
import com.chak.sc.utils.DomainErrors
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.badRequest
import org.springframework.web.reactive.function.server.ServerResponse.status
import org.springframework.web.reactive.function.server.bodyValueAndAwait

data class CustomerIdShouldBeInteger(val idPathVar: String?) : DomainErrors {
    override suspend fun response(): ServerResponse =
        badRequest()
            .bodyValueAndAwait(ErrorDTO("Customer Id $idPathVar must be integer"))
}

data class CustomerIdShouldBePositive(val id: Int) : DomainErrors {
    override suspend fun response(): ServerResponse =
        badRequest()
            .bodyValueAndAwait(ErrorDTO("Customer Id $id must be > 0"))
}

data class CustomerNotFound(val id: Int) : DomainErrors {
    override suspend fun response(): ServerResponse =
        status(HttpStatus.NOT_FOUND)
            .bodyValueAndAwait(ErrorDTO("No customer found against customer id $id"))
}

data class ServerError(val throwable: Throwable) : DomainErrors {
    override suspend fun response(): ServerResponse =
        status(HttpStatus.INTERNAL_SERVER_ERROR)
            .bodyValueAndAwait(ErrorDTO(throwable.localizedMessage))
}


