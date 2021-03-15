package com.chak.sc.com.chak.sc.product

import com.chak.sc.com.chak.sc.product.service.ProductService
import org.koin.dsl.module

val productModule = module {
    single { ProductService() }
}