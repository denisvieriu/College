package model.Statement;

import exceptions.DivideByZeroException;
import exceptions.InvalidOperandException;
import exceptions.NotExistingException;
import model.Expressions.Exp;
import utils.*;
import utils.adt.MyIDictionary;
import utils.adt.MyIHeap;
import utils.adt.MyIStack;

public class IfStmt implements IStmt {
    private Exp exp;
    private IStmt thenS;
    private IStmt elseS;

    public IfStmt(Exp e, IStmt t, IStmt el){
        exp = e;
        thenS = t;
        elseS = el;
    }

    @Override
    public String toString(){
        return "IF("+exp.toString()+")THEN("+thenS.toString()+")ELSE("+elseS.toString()+")";
    }

    @Override
    public PrgState execute(PrgState state) throws DivideByZeroException, InvalidOperandException, NotExistingException {
        MyIDictionary<String,Integer> symbolT = state.getSymTable();
        MyIStack<IStmt> exStack = state.getExeStack();
        MyIHeap<Integer, Integer> heap = state.getHeap();
        int res = exp.eval(symbolT, heap);
        if (res != 0) {
            exStack.push(thenS);
        }
        else {
            exStack.push(elseS);
        }
        //return state;

        return null;
    }
}
