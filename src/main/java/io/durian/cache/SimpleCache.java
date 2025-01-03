package io.durian.cache;

import lombok.SneakyThrows;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Boolean.TRUE;

public class SimpleCache<V> implements Cache<V> {
    final int capacity;
    final ConcurrentHashMap<String, V> cacheMap;
    final LinkedHashMap<String, Boolean> accessOrder;
    final ReentrantLock lock;

    public SimpleCache(int capacity) {
        this.capacity = capacity;
        this.cacheMap = new ConcurrentHashMap<>(capacity);
        this.accessOrder = new LinkedHashMap<>(capacity, 0.75f, true);
        this.lock = new ReentrantLock();
    }

    public void put(String key, V value) {
        try {
            lock.lock();
            if (cacheMap.containsKey(key)) {
                accessOrder.put(key, TRUE);
                cacheMap.put(key, value);
                return;
            }
            if (cacheMap.size() >= capacity) {
                Iterator<String> iterator = accessOrder.keySet().iterator();
                if (iterator.hasNext()) {
                    String eldestKey = iterator.next();
                    cacheMap.remove(eldestKey);
                    accessOrder.remove(eldestKey);
                }
            }
            cacheMap.put(key, value);
            accessOrder.put(key, TRUE);
        } finally {
            lock.unlock();
        }
    }

    public V get(String key) {
        if (!cacheMap.containsKey(key))
            return null;
        try {
            lock.lock();
            accessOrder.put(key, TRUE);
        } finally {
            lock.unlock();
        }
        return cacheMap.get(key);
    }

    @SneakyThrows
    @Override
    public V get(String key, CacheFunction<V> function) {
        V value = cacheMap.get(key);
        if (value != null) {
            try {
                lock.lock();
                accessOrder.put(key, TRUE);
            } finally {
                lock.unlock();
            }
            return value;
        }
        value = function.apply(key);
        put(key, value);
        return value;
    }


}
