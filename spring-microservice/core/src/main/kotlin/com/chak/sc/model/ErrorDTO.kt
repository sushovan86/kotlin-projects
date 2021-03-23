package com.chak.sc.model

import java.util.*

data class ErrorDTO(
    val timestamp: Date,
    val path: String,
    val requestId: String,
    val message: String = ""
) {

    fun toMap(): MutableMap<String, Any> = mutableMapOf(
        "timestamp" to timestamp,
        "path" to path,
        "requestId" to requestId,
        "message" to message
    )
}