package com.chak.sc

import com.chak.sc.gatewayroutes.GatewayConfig
import com.chak.sc.gatewayroutes.gatewayRoutes
import com.chak.sc.utils.errorBeanDefinitions
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.support.beans

@SpringBootApplication(exclude = [R2dbcAutoConfiguration::class])
@EnableEurekaClient
class ServiceGatewayApplication

fun main(args: Array<String>) {
    runApplication<ServiceGatewayApplication>(*args) {
        addInitializers(errorBeanDefinitions)
        addInitializers(gatewayInitializers())
    }
}

val routeToServiceMap: Map<String, String> = mapOf(
    "/customers/**" to "customer-service",
    "/products/**" to "product-service",
    "/inventories/**" to "product-service",
    "/orders/**" to "order-service"
)

fun gatewayInitializers() = beans {
    bean<GatewayConfig>()
    bean {
        gatewayRoutes(routeToServiceMap, ref(), ref())
    }
}
