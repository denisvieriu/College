package repository;

import java.util.ArrayList;
import model.*;

public class Repository implements IRepository {
    private ArrayList<Exp> aL;
    private int n;

    public Repository(){
        aL = new ArrayList<>();
        n = 0;
    }

    @Override
    public void addExp(Exp e){
        aL.add(e);
    }

    @Override
    public Exp getExp(){
        return aL.get(n++);
    }

    @Override
    public boolean notFull(){
        return n < aL.size();
    }

}
