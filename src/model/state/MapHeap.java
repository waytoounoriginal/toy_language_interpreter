package model.state;

import model.exceptions.HeapException;
import model.state.structures.m_HashMap;
import model.value.Value;

import java.util.HashMap;
import java.util.Map;

public class MapHeap<K extends Integer, V extends Value> implements m_HashMap<K, V> {
    private m_HashMap<K, V> heapMap;
    private Integer firstFreeAddress;


    public MapHeap() {
        firstFreeAddress = 1;
        heapMap = new MyHashMap<>();
    }

    @Override
    public synchronized void setContent(Map<K, V> newHeap) {
        heapMap.setContent(newHeap);
    }

    @Override
    public synchronized Map<K, V> getContent() {
        return heapMap.getContent();
    }

    @Override
    public void removeKey(K key) {
        heapMap.removeKey(key);
    }

    @Override
    public synchronized boolean isDefined(K key) {
        return heapMap.isDefined(key);
    }

    @Override
    public synchronized V getValue(K key) {
        if(!isDefined(key)) {
            throw new HeapException("The address is not present in the heap memory");
        }
        return heapMap.getValue(key);
    }

    @Override
    public synchronized void put(K key, V value) {
        heapMap.put(key, value);
    }

    public synchronized MapHeap<K, V> deepCopy() {
        MapHeap<K, V> nHeap = new MapHeap<K, V>();

        nHeap.firstFreeAddress = firstFreeAddress;
        for(var k : heapMap.getContent().keySet()) {
            nHeap.put(k, heapMap.getValue(k));
        }

        return nHeap;
    }

    public synchronized String toLogString() {
        StringBuilder sb = new StringBuilder();
        for (var key : heapMap.getContent().keySet()) {
            sb.append("   ")
                    .append(key)
                    .append(" -> ")
                    .append(heapMap.getValue(key).getType())
                    .append(" -> ")
                    .append(heapMap.getValue(key))
                    .append("\n");
        }
        return sb.toString();
    }

    public synchronized Integer allocate() {
        return firstFreeAddress++;
    }

    @Override
    public synchronized String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (var key : heapMap.getContent().keySet()) {
            sb.append(key)
                .append(" -> ")
                .append(heapMap.getValue(key).getType())
                .append(" -> ")
                .append(heapMap.getValue(key))
                .append(" ");
        }

        sb.append('}');
        return sb.toString();
    }
}
