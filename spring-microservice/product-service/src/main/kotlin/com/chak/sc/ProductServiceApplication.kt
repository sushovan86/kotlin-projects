package com.chak.sc

import com.chak.sc.routes.ProductHandler
import com.chak.sc.routes.productRoutes
import com.chak.sc.utils.errorBeanDefinitions
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.support.beans

@SpringBootApplication
@EnableEurekaClient
class ProductServiceApplication

fun main(args: Array<String>) {
    runApplication<ProductServiceApplication>(*args) {
        addInitializers(errorBeanDefinitions)
        addInitializers(productBeans)
    }
}

val productBeans = beans {
    bean<ProductHandler>()
    bean {
        productRoutes(ref())
    }
}