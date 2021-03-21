package com.ks.hashing.service.impl;

import com.ks.hashing.exception.ProcessingException;
import com.ks.hashing.repository.PhoneRepository;
import com.ks.hashing.service.HashService;
import com.ks.hashing.service.PhoneService;
import com.ks.hashing.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.ks.hashing.config.SpringAsyncConfig.TASK_EXECUTOR;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;


@Service
@Slf4j
public class PhoneServiceImpl implements PhoneService {
    private static final String ERROR_PHONE_NOT_FOUND = "Phone not found by hash ";

    @Value("${hash.salt}")
    private String hashSalt;
    @Value("${hash.default.cache.size}")
    private int defaultHashCacheSize;

    //TODO: if DB dynamicaly updated with phones
    // then it could be possible to configure quartz scheduler for async merging cache with db  data
    private Map<String, String> phonesAndHashes;
    private Map<String, Map<String, String>> hashesAndPhones;

    @Autowired
    private HashService hashService;
    @Autowired
    private PhoneRepository phoneRepository;

    @Override
    public String getHashCode(String cellPhoneId) {
        if (phonesAndHashes.containsKey(cellPhoneId)) {
            return phonesAndHashes.get(cellPhoneId);
        } else {
            String hashCode = hashService.generateHashCode(cellPhoneId, hashSalt);
            addToCache(cellPhoneId, hashCode);
            log.debug("Generated hashCode: {} for phone number: {}", hashCode, cellPhoneId);
            return hashCode;
        }


    }

    @Override
    public String getPhoneNumber(String hashCode) {
        if (hashesAndPhones.containsKey(hashCode)) {
            Map<String, String> phones = hashesAndPhones.get(hashCode);
            int countOfPhonesByHash = phones.size();
            if (countOfPhonesByHash > INTEGER_ONE) {
                log.error("Hash collisions for hashCode: {} exists {} phones: {}",
                        hashCode, countOfPhonesByHash, StringUtils.join(phones));
                throw new ProcessingException("More than one phone number for hash");
            }
            return gerFistPhone(phones);
        }
        log.error(ERROR_PHONE_NOT_FOUND + hashCode);
        throw new ProcessingException(ERROR_PHONE_NOT_FOUND);
    }

    private String gerFistPhone(Map<String, String> phones) {
        return phones.keySet().stream().findFirst().orElseThrow(() -> new ProcessingException(ERROR_PHONE_NOT_FOUND));
    }

    @Async(TASK_EXECUTOR)
    private void addToCache(String cellPhoneId, String hashCode) {
        phonesAndHashes.putIfAbsent(cellPhoneId, hashCode);
        if (hashesAndPhones.containsKey(hashCode)) {
            hashesAndPhones.get(hashCode)
                    .putIfAbsent(cellPhoneId, cellPhoneId);
        } else {
            hashesAndPhones.putIfAbsent(hashCode, CommonUtils.toConcurrentMap(cellPhoneId, cellPhoneId));
        }
        //TODO: add storing phone into DB
    }

    @PostConstruct
    void initializeCache() {
        this.phonesAndHashes = new ConcurrentHashMap<>(defaultHashCacheSize);
        this.hashesAndPhones = new ConcurrentHashMap<>(defaultHashCacheSize);
        phoneRepository.findAll().forEach(this::initLoadCacheData);
        log.debug(StringUtils.join(phonesAndHashes));
    }

    private void initLoadCacheData(String phoneNumber) {
        String hashCode = hashService.generateHashCode(phoneNumber, this.hashSalt);
        this.phonesAndHashes.put(phoneNumber, hashCode);
        this.hashesAndPhones.put(hashCode, CommonUtils.toConcurrentMap(phoneNumber, phoneNumber));
    }
}
