package org.arfna.util.datastructure;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class TimedHashMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;
    private final Map<K, LocalDateTime> timeAddedMap;
    private final long secondsTillExpiry;
    private final boolean refreshOnAdd;

    public TimedHashMap(long secondsTillExpiry, boolean refreshOnAdd) {
        this.map = new HashMap<>();
        this.timeAddedMap = new HashMap<>();
        this.secondsTillExpiry = secondsTillExpiry;
        this.refreshOnAdd = refreshOnAdd;
    }

    public TimedHashMap(long secondsTillExpiry) {
        this(secondsTillExpiry, false);
    }

    @Override
    public int size() {
        refreshMap();
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return this.get(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        refreshMap();
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        if (!isExpired((K) key)) {
            return map.get(key);
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        if (refreshOnAdd)
            refreshMap();
        map.put(key, value);
        timeAddedMap.put(key, LocalDateTime.now());
        return value;
    }

    @Override
    public V remove(Object key) {
        timeAddedMap.remove(key);
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        timeAddedMap.clear();
        map.clear();
    }

    @Override
    public Set<K> keySet() {
        refreshMap();
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        refreshMap();
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        refreshMap();
        return map.entrySet();
    }

    private void refreshMap() {
        Set<K> keySet = new HashSet<>(map.keySet());
        keySet.forEach(this::isExpired);
    }

    /**
     * Will also REMOVE expired element if found.
     * @param key key from map
     * @return true if object is expired, false if not found or not expired
     */
    private boolean isExpired(K key) {
        LocalDateTime timeAdded = timeAddedMap.get(key);
        if (timeAdded == null)
            return false;
        if (Duration.between(timeAdded, LocalDateTime.now()).getSeconds() > secondsTillExpiry) {
            timeAddedMap.remove(key);
            map.remove(key);
            return true;
        }
        return false;
    }

}
