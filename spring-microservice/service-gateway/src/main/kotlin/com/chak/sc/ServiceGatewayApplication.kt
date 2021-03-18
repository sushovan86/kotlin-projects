package com.chak.sc

import com.chak.sc.gatewayroutes.GatewayConfig
import com.chak.sc.gatewayroutes.gatewayRoutes
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.support.beans

@SpringBootApplication
@EnableEurekaClient
class ServiceGatewayApplication

fun main(args: Array<String>) {
    runApplication<ServiceGatewayApplication>(*args) {
        addInitializers(gatewayInitializers())
    }
}

fun gatewayInitializers() = beans {
    bean<GatewayConfig>()
    bean {
        gatewayRoutes(ref(), ref())
    }
}
