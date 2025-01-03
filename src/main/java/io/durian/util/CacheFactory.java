package io.durian.util;

import static java.lang.System.getProperty;

public class CacheFactory {

    public static <V> Cache<V> create() {
        int size = Integer.parseInt(getProperty("durian.cache.size", "100"));
        return switch (getProperty("durian.cache.type", "undef")) {
            case "no-cache" -> new NoCache<>();
            case "lru-cache" -> new LRUCache<>(size);
            case "no-limit-cache" -> new NoLimitCache<>();
            case "guava-cache" -> new GuavaCache<>(size);
            default -> classCanBeLoaded("com.google.common.cache.CacheBuilder") ?
                    new GuavaCache<>(size) :
                    new NoCache<>();
        };
    }

    static boolean classCanBeLoaded(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
