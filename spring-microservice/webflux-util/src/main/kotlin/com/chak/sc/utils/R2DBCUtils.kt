package com.chak.sc.utils

import io.r2dbc.spi.Row
import org.springframework.data.r2dbc.convert.R2dbcConverter

inline fun <reified T> R2dbcConverter.read(row: Row, prefix: String): T? =
    read(T::class.java,
        object : Row {
            override fun <T : Any?> get(index: Int, type: Class<T>): T? = row[index, type]
            override fun <T : Any?> get(name: String, type: Class<T>): T? = row["$prefix$name", type]
        }
    )