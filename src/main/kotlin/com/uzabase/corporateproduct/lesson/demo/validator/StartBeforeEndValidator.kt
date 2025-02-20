package com.uzabase.corporateproduct.lesson.demo.validator

import com.uzabase.corporateproduct.lesson.demo.controller.TimeRangeRequest
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class StartBeforeEndValidator : ConstraintValidator<StartBeforeEnd, TimeRangeRequest> {
    override fun isValid(value: TimeRangeRequest?, context: ConstraintValidatorContext): Boolean {
        if (value == null) return true
        return value.start < value.end
    }
}
