package com.chak.sc.kotlingraphql.graphql.config

import com.expediagroup.graphql.directives.KotlinDirectiveWiringFactory
import com.expediagroup.graphql.hooks.SchemaGeneratorHooks
import graphql.scalars.ExtendedScalars
import graphql.schema.GraphQLType
import org.springframework.beans.factory.BeanFactoryAware
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.isSubclassOf

class CustomTypeRegistration(override val wiringFactory: KotlinDirectiveWiringFactory) : SchemaGeneratorHooks {

    override fun willGenerateGraphQLType(type: KType): GraphQLType? =
        when (type.classifier) {
            LocalDate::class -> ExtendedScalars.Date
            LocalDateTime::class -> ExtendedScalars.DateTime
            else -> null
        }

    override fun isValidSuperclass(kClass: KClass<*>): Boolean =
        when {
            kClass.isSubclassOf(BeanFactoryAware::class) -> false
            else -> super.isValidSuperclass(kClass)
        }
}