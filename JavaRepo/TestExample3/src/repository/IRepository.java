package repository;

import model.*;
public abstract class IRepository {
    abstract public Exp getExp();
    abstract public void addExp(Exp exp);
    abstract public boolean isDone();
    abstract public Iterable<Exp> getList();
}
