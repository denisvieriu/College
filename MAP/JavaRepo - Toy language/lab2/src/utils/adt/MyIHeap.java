package utils.adt;

import java.util.Map;

public interface MyIHeap<K,V> {
    public V get(K address);
    public void add(K address, V content);
    public boolean contains(K address);
    public void update(K address, V content);
    public Iterable<K> getAll();
    public void setContent(Map<K, V> heap);
    public Map<K,V> getContent();
}
