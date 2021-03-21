package com.ks.hashing.api;

import com.ks.hashing.facade.PhoneFacade;
import com.ks.hashing.validator.PhoneValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/phones", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class CellPhoneController {

    private final PhoneFacade phoneFacade;
    private final PhoneValidator phoneValidator;

    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @GetMapping(path = "{cellPhoneId}/hashes")
    public ResponseEntity<String> getHashCodeByCellPhoneNumber(@PathVariable String cellPhoneId) {
        List<ObjectError> errorList = phoneFacade.validate(cellPhoneId, phoneValidator);
        phoneFacade.processValidationException(errorList);
        String hashCode = phoneFacade.getHash(cellPhoneId);
        log.debug("HashCode: {} for cellphone: {}", hashCode, cellPhoneId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(hashCode);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "hashes/{hashCode}")
    public ResponseEntity<String> getPhoneByHashCode(@PathVariable String hashCode) {
        String cellPhoneId = phoneFacade.getPhone(hashCode);
        log.debug("Cellphone: {} for hashCode: {}", cellPhoneId, hashCode);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(cellPhoneId);
    }
}
