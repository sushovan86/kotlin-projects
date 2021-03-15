package com.chak.sc

import com.chak.sc.com.chak.sc.product.productModule
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.request.*
import org.jetbrains.exposed.sql.Database
import org.koin.ktor.ext.koin
import org.slf4j.event.Level

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.configure(testing: Boolean = false) {

    initDB()

    if (testing) {
        callLoging()
    }

    compression()
    contentNegotiation()

    koin {
        modules(productModule)
    }

}

const val HIKARI_CONFIG_KEY = "ktor.deployment.hikariconfig"

fun Application.initDB() {
    val configPath = environment.config.property(HIKARI_CONFIG_KEY).getString()
    val dbConfig = HikariConfig(configPath)
    val dataSource = HikariDataSource(dbConfig)
    Database.connect(dataSource)
}

private fun Application.contentNegotiation() {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }
}

private fun Application.callLoging() {
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }
}

private fun Application.compression() {
    install(Compression) {
        gzip {
            priority = 1.0
        }
        deflate {
            priority = 10.0
            minimumSize(1024) // condition
        }
    }
}



