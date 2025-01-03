package io.durian.util;

public interface Cache<V> {
    V get(String key, CacheFunction<V> function);
}
