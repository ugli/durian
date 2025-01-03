package io.durian.cache;

import com.google.common.cache.CacheBuilder;

import java.util.concurrent.ExecutionException;

public class GuavaCache<V> implements Cache<V> {
    private final com.google.common.cache.Cache<String, V> cache;

    public GuavaCache(int size) {
        cache = CacheBuilder.newBuilder()
                .maximumSize(size)
                .build();
    }

    @Override
    public V get(String key, CacheFunction<V> function) {
        try {
            return cache.get(key, () -> {
                try {
                    return function.apply(key);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
