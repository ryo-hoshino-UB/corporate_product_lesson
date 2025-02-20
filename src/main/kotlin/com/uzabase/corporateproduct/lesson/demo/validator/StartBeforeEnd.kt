package com.uzabase.corporateproduct.lesson.demo.validator

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [StartBeforeEndValidator::class])
annotation class StartBeforeEnd(
    val message: String = "start must be before end",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)
