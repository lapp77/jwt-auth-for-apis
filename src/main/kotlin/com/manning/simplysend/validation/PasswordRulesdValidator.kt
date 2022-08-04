package com.manning.simplysend.validation

import org.passay.*
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class PasswordRulesdValidator : ConstraintValidator<Password, String> {
    private val validator: PasswordValidator

    init {
        val rules: MutableList<Rule> = ArrayList()
        rules.add(LengthRule(8, 16))
        rules.add(
            CharacterCharacteristicsRule(
                3,
                CharacterRule(EnglishCharacterData.UpperCase, MIN_UPPER_CASE_CHARS),
                CharacterRule(EnglishCharacterData.Digit, MIN_DIGIT_CASE_CHARS),
                CharacterRule(EnglishCharacterData.Special, MIN_SPECIAL_CASE_CHARS)
            )
        )
        validator = PasswordValidator(rules)
    }

    override fun isValid(password: String, context: ConstraintValidatorContext): Boolean {
        return validator.validate(PasswordData(password)).isValid
    }

    companion object {
        private const val MIN_SPECIAL_CASE_CHARS = 1
        private const val MIN_UPPER_CASE_CHARS = 1
        private const val MIN_DIGIT_CASE_CHARS = 1
    }
}