package ru.otus.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCache<K, V> implements HwCache<K, V> {
    //Надо реализовать эти методы
    private static final Logger logger = LoggerFactory.getLogger(HWCacheDemo.class);
    private final Map<K, V> cache = new HashMap<>();//WeakHashMap<>();
    private final List<HwListener<K, V>> listeners = new ArrayList<>();

    private HwListener<K, V> listener = new HwListener<K, V>(){
        @Override
        public void notify(K key, V value, String action) {
            logger.info("key:{}, value:{}, action: {}", key, value, action);
        }

    };

    @Override
    public void put(K key, V value) {

        cache.put(key, value);

        listener.notify(key,value,"put");

    }

    @Override
    public void remove(K key) {

      cache.remove(key);
      listener.notify(key,null,"put");

    }

    @Override
    public V get(K key) {

        return cache.get(key);


    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }
}