package io.durian.cache;

import lombok.SneakyThrows;

public class NoCache<V> implements Cache<V> {
    @SneakyThrows
    @Override
    public V get(String key, CacheFunction<V> function) {
        return function.apply(key);
    }
}
