package utils.adt;

import java.util.Map;

public interface MyIDictionary<K,V> {
    public V get(K key);
    public boolean contains(K key);
    public void put(K key, V val);
    public void update(K key, V val);
    public Iterable<K> getAll();
    public Map<K,V> getContent();
    public void setContent(Map<K, V> symTbl);
}
