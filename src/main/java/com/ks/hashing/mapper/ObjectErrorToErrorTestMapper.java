package com.ks.hashing.mapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;

import java.util.List;

@Component
public class ObjectErrorToErrorTestMapper {
    public String map(List<ObjectError> errorList) {
        return errorList.stream()
                .map(error -> error.getObjectName() + StringUtils.EMPTY + error.getCode() + StringUtils.EMPTY + error.getDefaultMessage())
                .reduce(StringUtils.EMPTY, (partialText, element) -> partialText + StringUtils.EMPTY + element);
    }
}
