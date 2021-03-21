package com.ks.hashing.service;

public interface HashService {
    String generateHashCode(String cellPhoneId, String salt);

    String getValueForHash(String cellPhoneId, String salt);
}
