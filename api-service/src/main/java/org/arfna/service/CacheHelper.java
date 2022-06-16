package org.arfna.service;

import org.apache.commons.lang.RandomStringUtils;
import org.arfna.database.entity.Subscriber;
import org.arfna.util.datastructure.TimedHashMap;

import java.util.Map;
import java.util.Optional;

public class CacheHelper {

    private static final Map<String, Object> COOKIE_CACHE = new TimedHashMap<>(4 * 60 * 60, true);

    private CacheHelper() {
        // restrict instantiation
    }

    public static String addValue(Object value) {
        String generatedKey = RandomStringUtils.randomAlphanumeric(30);
        while (COOKIE_CACHE.containsKey(generatedKey)) {
            generatedKey = RandomStringUtils.randomAlphanumeric(30);
        }
        COOKIE_CACHE.put(generatedKey, value);
        return generatedKey;
    }

    public static Optional<Subscriber> getAsSubscriber(String name) {
        Object value = COOKIE_CACHE.get(name);
        if (value instanceof Subscriber) {
            return Optional.of((Subscriber) value);
        }
        return Optional.empty();
    }
}
