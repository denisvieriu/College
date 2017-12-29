package utils.adt;

import java.util.Map;

public interface MyIFileTable<K, V> {
    public void add(K key, V val);
    public void remove(K key);
    public V get(K key);
    public Iterable<K> getAll();
    public Iterable<V> getValues();
    public boolean contains(K key);
    public Map<K, V> getContent();
    public void clear();
}
