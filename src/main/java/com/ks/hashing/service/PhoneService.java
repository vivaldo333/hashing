package com.ks.hashing.service;

public interface PhoneService {

    String getHashCode(String cellPhoneId);

    String getPhoneNumber(String hashCode);
}
