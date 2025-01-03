package io.durian.util;

@FunctionalInterface
public interface CacheFunction<V> {
    V apply(String key) throws Exception;
}
