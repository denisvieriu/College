package repository;

import model.*;

public interface IRepository {
    public void addExp(Exp exp);
    public Exp getExp();
    public boolean notFull();
}
