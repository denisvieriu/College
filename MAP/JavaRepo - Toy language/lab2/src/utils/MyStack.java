package utils;

import utils.adt.MyIStack;

import java.util.Deque;
import java.util.ArrayDeque;

public class MyStack<T> implements MyIStack<T> {
    private Deque<T> stack;
    public MyStack(){
        stack = new ArrayDeque<>();
    }


    public void push(T e){
        stack.push(e);
    }

    public T pop(){
        return stack.pop();
    }

    public boolean isEmpty(){
        return stack.size() == 0;
    }

    @Override
    public String toString(){
        StringBuffer stb = new StringBuffer();
        for(T e : stack){
            stb.append(e.toString());
            stb.append('\n');
        }
        if(stb.length() > 0)
            stb.deleteCharAt(stb.length() - 1);
        return stb.toString();
    }

    @Override
    public void clear()
    {
        stack.clear();
    }

    @Override
    public Iterable<T> getAll(){
        return stack;
    }


}
