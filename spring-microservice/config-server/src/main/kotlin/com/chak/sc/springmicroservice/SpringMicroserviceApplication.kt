package com.chak.sc.springmicroservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer

@SpringBootApplication
@EnableConfigServer
class SpringMicroserviceApplication

fun main(args: Array<String>) {
	runApplication<SpringMicroserviceApplication>(*args)
}
