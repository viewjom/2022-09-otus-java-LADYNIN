package ru.otus.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.json.dataprocessor.ProcessorImpl;
import ru.otus.json.model.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCache<K, V> implements HwCache<K, V> {
    //Надо реализовать эти методы
    private static final Logger logger = LoggerFactory.getLogger(MyCache.class);
    private final Map<K, V> cache = new HashMap<>();//WeakHashMap<>();
    private final List<HwListener<K, V>> listeners = new ArrayList<>();
    ProcessorImpl jsonProcessor = new ProcessorImpl();
    private HwListener<K, V> listener = new HwListener<K, V>(){
        @Override
        public void notify(K key, V value, String action) {



            logger.info("zip:{}{}, action: {}", key, value, action);
            //logger.info("key:{}, value:{}, action: {}", key, value, action);
        }
    };

    @Override
    public void put(K key, V value) {

       var jp = jsonProcessor.getCount((List<Result>) value);

        cache.put(key, value);

        listener.notify(key, (V) (", всего адресов:" + jp),"Запись в CACHE");
    }

    @Override
    public void remove(K key) {

      cache.remove(key);
      listener.notify(key,(V)"","Удаление из CACHE");

    }

    @Override
    public V get(K key) {
        listener.notify(key,(V)"","Чтение из CACHE");
        return cache.get(key);

    }

    @Override
    public Boolean isEmpty() {

        return cache.isEmpty();

    }

    @Override
    public Boolean containsKey(K key) {

        return cache.containsKey(key);

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