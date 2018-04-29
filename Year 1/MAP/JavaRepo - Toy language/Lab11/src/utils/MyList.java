package utils;

import utils.adt.MyIList;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T> {
    private List<T> list;

    public MyList(){
        list = new ArrayList<>();
    }

    @Override
    public void add(T el) {
        list.add(el);
    }

    @Override
    public String toString()
    {
        StringBuffer stb = new StringBuffer();
        for (T e : list){
            stb.append(e);
            stb.append('\n');
        }
        if (stb.length() > 0){
            stb.deleteCharAt(stb.length() - 1);
        }
        return stb.toString();
    }

    @Override
    public Iterable<T> getAll(){
        return list;
    }

    @Override
    public void clear()
    {
        list.clear();
    }
}
