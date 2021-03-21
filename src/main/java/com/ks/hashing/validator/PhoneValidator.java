package com.ks.hashing.validator;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Component
public class PhoneValidator implements Validator {
    private static final String PHONE_PATTERN = "^380\\d{9}$";

    @Override
    public boolean supports(Class<?> clazz) {
        return String.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NotNull Object dto, @NotNull Errors errors) {
        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        Matcher matcher = pattern.matcher(dto.toString());
        if (isFalse(matcher.matches())) {
            String messageCode = "phone.number.not.valid";
            String messageDescription = "Cell phone number" + dto.toString() + " is invalid";
            errors.reject(messageCode, messageDescription);
        }
    }
}
