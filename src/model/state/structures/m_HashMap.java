package model.state.structures;

import model.value.Value;

import java.util.Map;

public interface m_HashMap<K, V> {
    boolean isDefined(K key);
    V getValue(K key);
    void put(K key, V value);
    void setContent(Map<K, V> newContent);
    Map<K, V> getContent();

    void removeKey(K key);

    m_HashMap<K, V> deepCopy();
}
