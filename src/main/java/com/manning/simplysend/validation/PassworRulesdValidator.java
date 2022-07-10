package com.manning.simplysend.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.CharacterCharacteristicsRule;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.Rule;

public class PassworRulesdValidator implements ConstraintValidator<Password, String> {
    
    private static final int MIN_SPECIAL_CASE_CHARS = 1;
    private static final int MIN_UPPER_CASE_CHARS = 1;
    private static final int MIN_DIGIT_CASE_CHARS = 1;

    private final PasswordValidator validator;

    public PassworRulesdValidator() {
        List<Rule> rules = new ArrayList<>();
        rules.add(new LengthRule(8, 16));
        rules.add(new CharacterCharacteristicsRule(3, 
            new CharacterRule(EnglishCharacterData.UpperCase, MIN_UPPER_CASE_CHARS),
            new CharacterRule(EnglishCharacterData.Digit, MIN_DIGIT_CASE_CHARS),
            new CharacterRule(EnglishCharacterData.Special, MIN_SPECIAL_CASE_CHARS))
        );

        validator = new PasswordValidator(rules);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {       
        return validator.validate(new PasswordData(password)).isValid();
    }    
}
