package com.chak.sc.kotlingraphql.graphql.config

import com.chak.sc.kotlingraphql.services.CustomerService
import com.expediagroup.graphql.spring.execution.DataLoaderRegistryFactory
import org.dataloader.DataLoaderRegistry

const val ADDRESS_DATA_FETCHER = "Address"

fun dataLoaderRegistryFactory(service: CustomerService): DataLoaderRegistryFactory =
    object : DataLoaderRegistryFactory {

        override fun generate(): DataLoaderRegistry = DataLoaderRegistry().apply {

            register(ADDRESS_DATA_FETCHER, null)

        }

    }

