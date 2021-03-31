package com.chak.sc.utils

import org.springframework.context.support.beans
import org.springframework.web.reactive.function.client.WebClient

val webclientBean = beans {
    bean {
        val host = env.getProperty("gateway.host", "localhost")
        val port = env.getProperty("gateway.port", "9292")

        WebClient.create("http://$host:$port")
    }
}