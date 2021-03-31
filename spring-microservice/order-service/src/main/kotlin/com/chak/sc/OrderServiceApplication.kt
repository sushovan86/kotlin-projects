package com.chak.sc

import com.chak.sc.route.OrderHandler
import com.chak.sc.route.orderRoutes
import com.chak.sc.utils.errorBeanDefinitions
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.support.beans

@SpringBootApplication
@EnableEurekaClient
class OrderServiceApplication

fun main(args: Array<String>) {
    runApplication<OrderServiceApplication>(*args) {
        addInitializers(errorBeanDefinitions)
        addInitializers(orderBeans)
    }
}

val orderBeans = beans {

    bean<OrderHandler>()
    bean {
        orderRoutes(ref())
    }
}