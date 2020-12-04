package com.chak.sc.kotlingraphql

import com.chak.sc.kotlingraphql.graphql.config.CustomTypeRegistration
import com.expediagroup.graphql.directives.KotlinDirectiveWiringFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.support.beans

@SpringBootApplication
class KotlinGraphqlApplication

fun main(args: Array<String>) {
    runApplication<KotlinGraphqlApplication>(*args) {
        addInitializers(configs())
    }
}

fun configs() = beans {
    bean {
        KotlinDirectiveWiringFactory()
    }
    bean {
        CustomTypeRegistration(ref())
    }
}