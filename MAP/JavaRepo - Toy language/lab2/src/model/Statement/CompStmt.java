package model.Statement;

import utils.*;
import utils.adt.MyIStack;

public class CompStmt implements IStmt {
    private IStmt first;
    private IStmt snd;

    public CompStmt(IStmt f1, IStmt f2)
    {
        first = f1;
        snd =f2;
    }

    @Override
    public String toString(){
        return "(" + first + ";" + snd + ")";
    }

    @Override
    public PrgState execute(PrgState state){
        MyIStack<IStmt> stk = state.getExeStack();
        stk.push(snd);
        stk.push(first);
        //return state;

        return null;
    }
}

