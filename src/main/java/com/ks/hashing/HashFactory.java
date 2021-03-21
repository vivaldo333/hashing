package com.ks.hashing;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import static org.apache.commons.codec.digest.MessageDigestAlgorithms.SHA3_512;
import static org.apache.commons.codec.digest.MessageDigestAlgorithms.SHA_1;
import static org.apache.commons.codec.digest.MessageDigestAlgorithms.SHA_256;

@Component
@Slf4j
public class HashFactory {
    private static final String ERROR_HASH_ALGORITM_EMPTY = "Hash algoritm is not defined";
    private static final String ERROR_HASH_ALGORITM_NOT_SUPPORTED = "Unsupported hashing algoritm";

    public DigestUtils getHashStrategy(String hashAlgoritm) {
        if (StringUtils.isEmpty(hashAlgoritm)) {
            throw new IllegalArgumentException(ERROR_HASH_ALGORITM_EMPTY);
        }

        switch (hashAlgoritm) {
            case SHA_1:
                return new DigestUtils(SHA_1);
            case SHA_256:
                return new DigestUtils(SHA_256);
            case SHA3_512:
                return new DigestUtils(SHA3_512);
            default:
        }
        log.error(ERROR_HASH_ALGORITM_NOT_SUPPORTED + hashAlgoritm);
        throw new IllegalArgumentException(ERROR_HASH_ALGORITM_NOT_SUPPORTED);
    }
}
