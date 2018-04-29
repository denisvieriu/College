package model.Statement;

import exceptions.DivideByZeroException;
import exceptions.InterpreterException;
import exceptions.InvalidOperandException;
import exceptions.NotExistingException;
import model.Expressions.Exp;
import utils.PrgState;
import utils.adt.MyIDictionary;
import utils.adt.MyIHeap;

public class WriteH implements IStmt{
    private String varName;
    private Exp expression;

    public WriteH(String _varName, Exp _expression){
        varName = _varName;
        expression = _expression;
    }

    @Override
    public PrgState execute(PrgState state) throws DivideByZeroException, InvalidOperandException, NotExistingException {
        MyIHeap<Integer, Integer> heap = state.getHeap();
        MyIDictionary<String, Integer> symTable = state.getSymTable();

        int r = expression.eval(symTable, heap);
        if (!symTable.contains(varName)) {
            throw new InterpreterException("String not found in symbol table");
        }

        if (!heap.contains(symTable.get(varName))) {
            throw new InterpreterException("Couldn't find the address in heap");
        }

        heap.update(symTable.get(varName), r);

        //return state;
        return null;
    }

    @Override
    public String toString(){
        return "writeH(" + varName + "," + expression + ")";
    }
}
