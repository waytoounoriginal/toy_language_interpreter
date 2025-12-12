package model.state;

import model.state.structures.m_HashMap;
import model.value.Value;

import java.util.HashMap;
import java.util.Map;

public class MyHashMap<K, V> implements m_HashMap<K, V> {
    private Map<K, V> innerMap;

    public MyHashMap() {
        innerMap = new HashMap<>();
    }

    @Override
    public boolean isDefined(K key) {
        return innerMap.containsKey(key);
    }

    @Override
    public V getValue(K key) {
        return innerMap.get(key);
    }

    @Override
    public void put(K key, V value) {
        innerMap.put(key, value);
    }

    @Override
    public void setContent(Map<K, V> newContent) {
        innerMap = newContent;
    }

    @Override
    public Map<K, V> getContent() {
        return innerMap;
    }

    @Override
    public void removeKey(K key) {
        innerMap.remove(key);
    }
}
