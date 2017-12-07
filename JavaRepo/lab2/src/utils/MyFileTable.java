package utils;

import utils.adt.MyIFileTable;

import java.util.HashMap;
import java.util.Map;

public class MyFileTable<K,V> implements MyIFileTable<K,V> {
    private Map<K, V> dict;
    public MyFileTable(){
        dict = new HashMap<>();
    }

    @Override
    public void add(K key, V val){
        dict.put(key, val);
    }

    @Override
    public void remove(K key){
        dict.remove(key);
    }

    @Override
    public V get(K key){
        return dict.get(key);
    }

    @Override
    public Iterable<K> getAll(){
        return dict.keySet();
    }

    @Override
    public Iterable<V> getValues(){
        return dict.values();
    }

    @Override
    public boolean contains(K key){
        return dict.containsKey(key);
    }

    @Override
    public Map<K, V> getContent(){ return dict; }
}
