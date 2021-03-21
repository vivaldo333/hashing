package com.ks.hashing.facade.impl;

import com.ks.hashing.facade.PhoneFacade;
import com.ks.hashing.mapper.ObjectErrorToErrorTestMapper;
import com.ks.hashing.service.PhoneService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import javax.validation.ValidationException;
import java.util.Collections;
import java.util.List;

@Service
public class PhoneFacadeImpl implements PhoneFacade {

    @Autowired
    private PhoneService phoneService;
    @Autowired
    private ObjectErrorToErrorTestMapper objectErrorToErrorTestMapper;

    @Override
    public <T> List<ObjectError> validate(T dto, Validator validator) {
        /*BeanPropertyBindingResult beanPropertyBindingResult = new BeanPropertyBindingResult(item, "item");
        validator.validate(item, beanPropertyBindingResult);*/
        DataBinder binder = getDataBinder(dto, validator);
        binder.validate();
        return getValidationErrors(binder.getBindingResult());
    }

    @Override
    public void processValidationException(List<ObjectError> errorList) {
        if (CollectionUtils.isNotEmpty(errorList)) {
            String errorMessage = objectErrorToErrorTestMapper.map(errorList);
            throw new ValidationException(errorMessage);
        }
    }

    @Override
    public String getHash(String cellPhoneId) {
        return phoneService.getHashCode(cellPhoneId);
    }

    @Override
    public String getPhone(String hashCode) {
        return phoneService.getPhoneNumber(hashCode);
    }

    private <T> DataBinder getDataBinder(T dto, Validator validator) {
        DataBinder binder = new DataBinder(dto);
        binder.setValidator(validator);
        return binder;
    }

    private List<ObjectError> getValidationErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return bindingResult.getAllErrors();
        }
        return Collections.emptyList();
    }
}
