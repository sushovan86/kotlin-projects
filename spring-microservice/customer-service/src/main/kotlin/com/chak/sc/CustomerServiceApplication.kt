package com.chak.sc

import com.chak.sc.routes.CustomerHandler
import com.chak.sc.routes.applicationRouter
import com.chak.sc.utils.errorAttributesFrom
import com.chak.sc.utils.errorBeanDefinitions
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.support.beans
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.ServerRequest
import java.io.PrintWriter
import java.io.StringWriter

@SpringBootApplication
@EnableEurekaClient
class CustomerServiceApplication

fun main(args: Array<String>) {
    runApplication<CustomerServiceApplication>(*args) {
        addInitializers(errorBeanDefinitions())
        addInitializers(applicationBeanDefinitions())
    }
}

fun applicationBeanDefinitions() = beans {
    bean<CustomerHandler>()
    bean {
        applicationRouter(ref())
    }
}

