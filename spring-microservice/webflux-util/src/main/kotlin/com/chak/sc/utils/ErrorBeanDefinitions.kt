package com.chak.sc.utils

import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes
import org.springframework.context.support.beans
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.ServerRequest
import java.io.PrintWriter
import java.io.StringWriter

val errorBeanDefinitions = beans {

    bean<DefaultErrorAttributes> {

        object : DefaultErrorAttributes() {

            override fun getErrorAttributes(
                request: ServerRequest?,
                options: ErrorAttributeOptions?
            ): MutableMap<String, Any> {

                val error: Throwable = super.getError(request)
                val errorAttributes: MutableMap<String, Any> = errorAttributesFrom(request, error.localizedMessage)
                if (options?.isIncluded(ErrorAttributeOptions.Include.STACK_TRACE) == true) {
                    addStackTrace(errorAttributes, error)
                }
                errorAttributes["status"] = HttpStatus.INTERNAL_SERVER_ERROR.value()

                return errorAttributes
            }

            private fun addStackTrace(errorAttributes: MutableMap<String, Any>, error: Throwable) {
                val stackTrace = StringWriter()
                error.printStackTrace(PrintWriter(stackTrace))
                stackTrace.flush()
                errorAttributes["trace"] = stackTrace.toString()
            }
        }
    }
}