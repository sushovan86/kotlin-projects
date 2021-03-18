package com.chak.sc.gatewayroutes

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.gateway.route.builder.filters
import org.springframework.cloud.gateway.route.builder.routes

class GatewayConfig(@Value("\${gateway.retry-count:5}") val retryCount: Int)

fun gatewayRoutes(
    routeLocatorBuilder: RouteLocatorBuilder,
    gatewayConfig: GatewayConfig
) =
    routeLocatorBuilder.routes {

        route {
            path("/customers/**")
            filters {
                retry(gatewayConfig.retryCount)
            }
            uri("lb://customer-service")
        }
    }

