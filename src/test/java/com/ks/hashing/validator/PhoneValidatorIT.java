package com.ks.hashing.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;


class PhoneValidatorTest {

    private PhoneValidator testInstance = new PhoneValidator();

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldReturnTrueWhenClassIsString() {
        boolean result = testInstance.supports(String.class);

        Assertions.assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenPhoneNumberIsMatchedWithPattern() {
        String phoneNumber = "380671112233";
        Errors error = new BeanPropertyBindingResult(phoneNumber, "phoneNumber");

        testInstance.validate(phoneNumber, error);

        Assertions.assertFalse(error.hasErrors());
    }

    @Test
    void shouldReturnTrueWhenPhoneNumberIsNotMatchedWithPattern() {
        String phoneNumber = "0671112233";
        Errors error = new BeanPropertyBindingResult(phoneNumber, "phoneNumber");

        testInstance.validate(phoneNumber, error);

        Assertions.assertTrue(error.hasErrors());
    }
}