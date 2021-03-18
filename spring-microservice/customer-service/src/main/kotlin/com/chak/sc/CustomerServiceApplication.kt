package com.chak.sc

import com.chak.sc.routes.CustomerHandler
import com.chak.sc.routes.applicationRouter
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.support.beans

@SpringBootApplication
@EnableEurekaClient
class CustomerServiceApplication

fun main(args: Array<String>) {
    runApplication<CustomerServiceApplication>(*args) {
        addInitializers(beanDefinitions())
    }
}

fun beanDefinitions() = beans {
    bean<CustomerHandler>()
    bean {
        applicationRouter(ref())
    }
}
