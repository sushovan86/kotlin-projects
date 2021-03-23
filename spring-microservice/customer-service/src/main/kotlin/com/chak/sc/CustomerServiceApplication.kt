package com.chak.sc

import com.chak.sc.routes.CustomerHandler
import com.chak.sc.routes.applicationRouter
import com.chak.sc.utils.errorBeanDefinitions
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.support.beans
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.web.reactive.config.WebFluxConfigurer

@SpringBootApplication
@EnableEurekaClient
class CustomerServiceApplication

fun main(args: Array<String>) {
    runApplication<CustomerServiceApplication>(*args) {
        addInitializers(errorBeanDefinitions)
        addInitializers(applicationBeanDefinitions)
    }
}

val applicationBeanDefinitions = beans {
    bean<CustomerHandler>()
    bean {
        applicationRouter(ref())
    }
    bean<WebFluxConfigurer> {
        object : WebFluxConfigurer {
            override fun configureHttpMessageCodecs(configurer: ServerCodecConfigurer) {
                configurer.defaultCodecs().enableLoggingRequestDetails(true)
            }
        }
    }
}

