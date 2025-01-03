package io.durian.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NoLimitCache<V> implements Cache<V> {

    Map<String, V> cache = new ConcurrentHashMap<>();

    @Override
    public V get(String key, CacheFunction<V> function) {
        return cache.computeIfAbsent(key, _key -> {
            try {
                return function.apply(key);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
