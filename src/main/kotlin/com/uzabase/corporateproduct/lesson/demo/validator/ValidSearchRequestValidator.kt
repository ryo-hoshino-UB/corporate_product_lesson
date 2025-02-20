package com.uzabase.corporateproduct.lesson.demo.validator

import com.uzabase.corporateproduct.lesson.demo.controller.SearchRequest
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class ValidSearchRequestValidator : ConstraintValidator<ValidSearchRequest, SearchRequest> {
    override fun isValid(value: SearchRequest?, context: ConstraintValidatorContext): Boolean {
        val isWordBlank = value?.word.isNullOrBlank()
        val isEmailBlank = value?.email.isNullOrBlank()

        return isWordBlank != isEmailBlank
    }
}
