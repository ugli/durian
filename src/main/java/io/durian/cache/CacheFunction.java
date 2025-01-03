package io.durian.cache;

@FunctionalInterface
public interface CacheFunction<V> {
    V apply(String key) throws Exception;
}
