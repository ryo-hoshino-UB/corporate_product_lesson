package com.uzabase.corporateproduct.lesson.demo.validator

import com.uzabase.corporateproduct.lesson.demo.controller.SearchRequest
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class ValidSearchRequestValidator : ConstraintValidator<ValidSearchRequest, SearchRequest> {
    override fun isValid(value: SearchRequest?, context: ConstraintValidatorContext): Boolean {
        if (value == null) return true

        val isWordBlank = value.word.isNullOrBlank()
        val isEmailBlank = value.email.isNullOrBlank()


        return when {
            isWordBlank && isEmailBlank -> {
                context.disableDefaultConstraintViolation()
                context.buildConstraintViolationWithTemplate("{com.uzabase.corporateproduct.lesson.demo.validator.ValidSearchRequest.both.empty}")
                    .addConstraintViolation()
                false
            }

            !isWordBlank && !isEmailBlank -> {
                context.disableDefaultConstraintViolation()
                context.buildConstraintViolationWithTemplate("{com.uzabase.corporateproduct.lesson.demo.validator.ValidSearchRequest.both.specified}")
                    .addConstraintViolation()
                false
            }

            else -> true
        }
    }
}
