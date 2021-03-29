package com.chak.sc.gatewayroutes

import com.chak.sc.ServiceGatewayApplication
import com.chak.sc.utils.getLogger
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.gateway.route.builder.PredicateSpec
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.gateway.route.builder.filters
import org.springframework.cloud.gateway.route.builder.routes

val logger = getLogger<ServiceGatewayApplication>()

class GatewayConfig(@Value("\${gateway.retry-count:5}") val retryCount: Int)

fun gatewayRoutes(
    routeLocatorBuilder: RouteLocatorBuilder,
    gatewayConfig: GatewayConfig
) =
    routeLocatorBuilder.routes {

        route {
            path("/customers/**")
            commonFilter(gatewayConfig)
            uri("lb://customer-service")
        }

        route {
            path("/products/**")
            commonFilter(gatewayConfig)
            uri("lb://product-service")
        }
    }

private fun PredicateSpec.commonFilter(gatewayConfig: GatewayConfig) =
    filters {

        retry(gatewayConfig.retryCount)

        filter { exchange, chain ->
            logger.debug(
                """--
                Request Id: ${exchange.request.id}
                Query Params: ${exchange.request.queryParams}
                Request Path: ${exchange.request.path}
                Accept-Type: ${exchange.request.headers.accept}
                Content-Type: ${exchange.request.headers.contentType}
            """.trimIndent()
            )
            chain.filter(exchange)
        }
    }

