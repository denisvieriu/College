package utils.adt;

public interface MyIList<T> {
    public void add(T el);
    public Iterable<T> getAll();
    public void clear();

}
