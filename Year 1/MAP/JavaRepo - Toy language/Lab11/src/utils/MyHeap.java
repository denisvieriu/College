package utils;

import utils.adt.MyIHeap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

public class MyHeap<K,V> implements MyIHeap<K,V>{
    private Map<K,V> hMap;

    public MyHeap(){
        hMap = new HashMap<>();
    }

    @Override
    public V get(K address){ return hMap.get(address); }

    @Override
    public void add(K address, V value){
        hMap.put(address, value);
    }

    @Override
    public boolean contains(K address){
        return hMap.containsKey(address);
    }

    @Override
    public void update(K address, V value){
        hMap.put(address,value);
    }

    @Override
    public Iterable<K> getAll() {
        return hMap.keySet();
    }

    @Override
    public void setContent(Map<K, V> heap) { hMap = heap; }

    @Override
    public Map<K,V> getContent(){
        return hMap;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        Iterator<Map.Entry<K, V>> iter = hMap.entrySet().iterator();
        while(iter.hasNext())
        {
            Map.Entry<K, V> entry = iter.next();
            sb.append(entry.getKey());
            sb.append('=').append('"');
            sb.append(entry.getValue());
            sb.append('"');
            if(iter.hasNext()){
                sb.append(',').append(' ');
            }
        }
        return sb.toString();
    }

    @Override
    public void clear()
    {
        hMap.clear();
    }

}
