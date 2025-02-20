package com.uzabase.corporateproduct.lesson.demo.validator

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ValidSearchRequestValidator::class])
annotation class ValidSearchRequest(
    val message: String = "{com.uzabase.corporateproduct.lesson.demo.validator.ValidSearchRequest.message}",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)
