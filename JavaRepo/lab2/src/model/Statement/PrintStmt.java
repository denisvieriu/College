package model.Statement;

import exceptions.DivideByZeroException;
import exceptions.InvalidOperandException;
import exceptions.NotExistingException;
import model.Expressions.Exp;
import utils.*;
import utils.adt.MyIDictionary;
import utils.adt.MyIHeap;
import utils.adt.MyIList;

public class PrintStmt implements IStmt {
    private Exp exp;

    public PrintStmt(Exp _exp) {
        exp = _exp;
    }

    @Override
    public String toString(){
        return "print(" + exp.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws DivideByZeroException, InvalidOperandException, NotExistingException {
        MyIList<Integer> list = state.getOut();
        MyIDictionary<String, Integer> dict = state.getSymTable();
        MyIHeap<Integer, Integer> heap = state.getHeap();
        int res = exp.eval(dict, heap);
        list.add(res);
        return state;
    }
}
