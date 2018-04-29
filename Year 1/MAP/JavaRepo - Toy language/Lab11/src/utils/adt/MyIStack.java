package utils.adt;

public interface MyIStack<T> {
    public void push(T e);
    public T pop();
    public boolean isEmpty();
    public Iterable<T> getAll();
    public void clear();
}
