package com.ks.hashing.utils;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class CommonUtils {
    private static final String ERROR_MESSAGE = "Impossible to initiate utility class";

    private CommonUtils() {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    public static Map<String, String> toConcurrentMap(String key, String value) {
        return Stream.of(key, value)
                .collect(Collectors.toConcurrentMap(Function.identity(), Function.identity(), (o, n) -> n));


    }
}
