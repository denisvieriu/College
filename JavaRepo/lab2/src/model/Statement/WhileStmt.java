package model.Statement;

import exceptions.DivideByZeroException;
import exceptions.InvalidOperandException;
import exceptions.NotExistingException;
import model.Expressions.Exp;
import utils.PrgState;
import utils.adt.MyIDictionary;
import utils.adt.MyIHeap;
import utils.adt.MyIStack;

public class WhileStmt implements IStmt{
    private Exp exp;
    private IStmt stmt;

    public WhileStmt(Exp _exp, IStmt _stmt){
        exp = _exp;
        stmt = _stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws DivideByZeroException, InvalidOperandException, NotExistingException {
        MyIDictionary<String, Integer> symTable = state.getSymTable();
        MyIHeap<Integer, Integer> heap = state.getHeap();
        MyIStack<IStmt> exeStack = state.getExeStack();

        int r = exp.eval(symTable, heap);
        if (r == 0) {
            return state;
        }
        else
        {
            exeStack.push(this);
            stmt.execute(state);
        }

        return state;
    }

    @Override
    public String toString(){
        return "(while(" + exp + ")" + stmt + ")";
    }
}
