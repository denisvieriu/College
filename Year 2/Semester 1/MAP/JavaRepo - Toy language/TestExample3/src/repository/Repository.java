package repository;

import model.*;
import java.util.ArrayList;

public class Repository extends IRepository{
    private ArrayList<Exp> aL;
    private int contor;

    public Repository(){
        aL = new ArrayList<>();
        contor = 0;
    }

    @Override
    public Exp getExp(){
        return aL.get(contor++);
    }

    @Override
    public boolean isDone(){
        return contor >= aL.size();
    }

    @Override
    public void addExp(Exp exp){
        aL.add(exp);
    }

    @Override
    public Iterable<Exp> getList(){
        return aL;
    }


}
