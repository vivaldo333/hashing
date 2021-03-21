package com.ks.hashing.facade;

import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import java.util.List;

public interface PhoneFacade {
    <T> List<ObjectError> validate(T dto, Validator validator);

    void processValidationException(List<ObjectError> errorList);

    String getHash(String cellPhoneId);

    String getPhone(String hashCode);
}
