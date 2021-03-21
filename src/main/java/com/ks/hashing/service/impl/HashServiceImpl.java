package com.ks.hashing.service.impl;

import com.ks.hashing.HashFactory;
import com.ks.hashing.service.HashService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HashServiceImpl implements HashService {

    @Autowired
    private HashFactory hashFactory;

    //SHA-1, SHA-2, SHA-3.
    @Value("${hash.algorithm}")
    private String hashAlgorithm;

    @Override
    public String generateHashCode(String cellPhoneId, String salt) {
        String phoneWithSalt = getValueForHash(cellPhoneId, salt);
        DigestUtils hasStrategy = hashFactory.getHashStrategy(this.hashAlgorithm);
        return hasStrategy.digestAsHex(phoneWithSalt);
    }

    public String getValueForHash(String cellPhoneId, String salt) {
        //better performance that String.concat() or "+"
        return new StringBuilder()
                .append(cellPhoneId)
                .append(salt)
                .toString();
    }
}
