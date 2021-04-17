package com.chak.sc.utils

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapBoth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.ok

suspend inline fun <T> Result<T, DomainErrors>.returnSingleResponse(serverRequest: ServerRequest): ServerResponse =
    this.mapBoth(
        { success ->
            when (success) {
                null -> NoDataAvailable.response(serverRequest)
                else -> ok().bodyValueAndAwait(success)
            }
        },
        { error -> error.response(serverRequest) }
    )

suspend inline fun <reified T : Any> Result<Flow<T>, DomainErrors>.returnFlowResponse(serverRequest: ServerRequest)
        : ServerResponse =
    this.mapBoth(
        { success -> flowResponse(success, serverRequest) },
        { error -> error.response(serverRequest) }
    )

@PublishedApi
internal suspend inline fun <reified T : Any> flowResponse(
    flow: Flow<T>,
    serverRequest: ServerRequest
): ServerResponse =

    when (MediaType.TEXT_EVENT_STREAM) {

        in serverRequest.headers().accept(),
        serverRequest.headers().contentType().orElse(null) -> ok().sse().bodyAndAwait(flow)

        else -> {
            val outputList: List<T> = flow.toList()
            if (outputList.isEmpty()) {
                NoDataAvailable.response(serverRequest)
            } else {
                ok().json().bodyValueAndAwait(outputList)
            }
        }
    }