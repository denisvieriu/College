package model.Expressions;

import exceptions.NotExistingException;
import utils.adt.MyIDictionary;
import utils.adt.MyIHeap;

public class VarExp extends Exp {
    String id;

    public VarExp(String _id){
        id = _id;
    }

    @Override
    public int eval(MyIDictionary<String, Integer> tbl,
                    MyIHeap<Integer, Integer> heap) throws NotExistingException
    {
        if (tbl.contains(id)){
            return tbl.get(id);
        }
        throw new NotExistingException("Not found");
    }

    @Override
    public String toString() {
        return id;
    }
}
