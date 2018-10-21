package model.Statement;

import exceptions.DivideByZeroException;
import exceptions.InvalidOperandException;
import exceptions.NotExistingException;
import model.Expressions.Exp;
import utils.*;
import utils.adt.MyIDictionary;
import utils.adt.MyIHeap;

public class AssignStmt implements IStmt {
    private String id;
    private Exp exp;

    public AssignStmt(String _id, Exp _exp){
        id = _id;
        exp = _exp;
    }

    @Override
    public String toString(){
        return id + "=" + exp.toString();
    }

    @Override
    public PrgState execute(PrgState state) throws DivideByZeroException, InvalidOperandException, NotExistingException {
        MyIDictionary<String,Integer> symTbl = state.getSymTable();
        MyIHeap<Integer, Integer> heap = state.getHeap();
        int val = exp.eval(symTbl, heap);
        if (symTbl.contains(id)){
            symTbl.update(id, val);
        }
        else {
            symTbl.put(id, val);
        }

        // return state;
        return null;
    }
}
