package utils;


import utils.adt.MyIDictionary;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MyDictionary<K,V> implements MyIDictionary<K,V> {
    private Map<K,V> hMap;

    public MyDictionary(){
        hMap = new HashMap<>();
    }

    @Override
    public V get(K key)
    {
        return this.hMap.get(key);
    }

    @Override
    public boolean contains(K key)
    {
        return this.hMap.containsKey(key);
    }

    @Override
    public void put(K key, V val)
    {
        this.hMap.put(key,val);
    }

    @Override
    public void update(K key, V val)
    {
        this.hMap.put(key,val);
    }

    @Override
    public Map<K,V> getContent(){
        return hMap;
    }

    @Override
    public String toString(){
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
    public void setContent(Map<K, V> symTbl) { hMap = symTbl; }

    @Override
    public Iterable<K> getAll(){
        return hMap.keySet();
    }
}
