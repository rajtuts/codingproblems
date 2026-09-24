//Here's a sample implementation of a generic LRU cache in Java:

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LRUCache<K, V> {
    private final int capacity;
    private final Map<K, V> cacheMap;
    private final LinkedList<K> keyList;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cacheMap = new HashMap<>();
        this.keyList = new LinkedList<>();
    }

    public synchronized void put(K key, V value) {
        if (cacheMap.containsKey(key)) {
            keyList.remove(key);
        } else if (cacheMap.size() == capacity) {
            K leastRecentlyUsedKey = keyList.removeFirst();
            cacheMap.remove(leastRecentlyUsedKey);
        }

        cacheMap.put(key, value);
        keyList.addLast(key);
    }

    public synchronized V get(K key) {
        V value = cacheMap.get(key);

        if (value != null) {
            keyList.remove(key);
            keyList.addLast(key);
        }

        return value;
    }

    public synchronized void clear() {
        cacheMap.clear();
        keyList.clear();
    }

    public synchronized int size() {
        return cacheMap.size();
    }

    public synchronized boolean containsKey(K key) {
        return cacheMap.containsKey(key);
    }
}
