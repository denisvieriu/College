package repository;

import model.Exp;

import java.util.ArrayList;

public class Repository implements IRepository {
    private ArrayList<Exp> aL;
    private int currentEl;

    public Repository() {
        aL = new ArrayList<Exp>();
        currentEl = 0;
    }

    @Override
    public void addExp(Exp e){
        aL.add(e);
    }

    public Exp getExp(){
        return aL.get(currentEl++);
    }

    public boolean notFull(){
        return currentEl < aL.size();
    }

}
