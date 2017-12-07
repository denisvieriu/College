package model.Expressions;

import exceptions.InterpreterException;
import utils.adt.MyIDictionary;
import utils.adt.MyIHeap;

public class ReadH extends Exp{
    private String varName;

    public ReadH(String _varName){
        varName = _varName;
    }

    @Override
    public int eval(MyIDictionary<String, Integer> symbolTable,
                    MyIHeap<Integer, Integer> heap){
        if (!symbolTable.contains(varName)) {
            throw new InterpreterException("Key" + varName + " is not in symbol table");
        }

        int val = symbolTable.get(varName);
        if (!heap.contains(val)){
            throw new InterpreterException("Couldn't find heap location");
        }

        return heap.get(val);
    }

    @Override
    public String toString() {
        return "readH(" + varName + ")";
    }




}
