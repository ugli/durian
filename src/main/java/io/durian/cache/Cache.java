package io.durian.cache;

public interface Cache<V> {
    V get(String key, CacheFunction<V> function);
}
