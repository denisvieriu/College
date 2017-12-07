package model.Statement;

import exceptions.DivideByZeroException;
import exceptions.InvalidOperandException;
import exceptions.NotExistingException;
import model.Expressions.Exp;
import utils.IDGenerator;
import utils.PrgState;
import utils.adt.MyIDictionary;
import utils.adt.MyIHeap;

public class NewH implements IStmt{
    private String var_name;
    private Exp expression;
    public NewH(String _var_name, Exp _expression){
        var_name = _var_name;
        expression = _expression;
    }

    @Override
    public PrgState execute(PrgState state) throws DivideByZeroException, InvalidOperandException, NotExistingException {
        MyIHeap<Integer,Integer> hp = state.getHeap();
        MyIDictionary<String,Integer> symTable = state.getSymTable();
        int id = IDGenerator.generate_id();
        hp.add(id,expression.eval(symTable, hp));
        if (symTable.contains(var_name)) {
            symTable.update(var_name, id);
        }
        else {
            symTable.put(var_name,id);
        }

        return state;
    }

    @Override
    public String toString(){
        return "NewH(" + var_name + ", " + expression + ")";
    }
}
