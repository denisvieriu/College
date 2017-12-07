package model;

import exceptions.DivideByZeroException;
import exceptions.InterpreterException;
import exceptions.InvalidOperandException;
import exceptions.NotExistingException;
import model.Expressions.Exp;
import model.Statement.IStmt;
import utils.FileData;
import utils.adt.MyIDictionary;
import utils.PrgState;
import utils.adt.MyIHeap;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStmt {
    private Exp expFileId;
    private String varName;

    public ReadFile(Exp _expFileId, String _varName){
        expFileId = _expFileId;
        varName = _varName;
    }

    @Override
    public PrgState execute(PrgState state) throws DivideByZeroException, InvalidOperandException, NotExistingException {
        MyIDictionary<String, Integer> dict = state.getSymTable();
        MyIHeap<Integer, Integer> heap = state.getHeap();
        // evaluate the variabile_id to a value
        int id = expFileId.eval(dict, heap);

        // get corresponing file_id
        FileData fd = state.getFileTable().get(id);
        try {
            BufferedReader br = fd.getHeader();
            String line = br.readLine();
            int value;
            if (line == null){
                value = 0;
            }
            else{
                value = Integer.parseInt(line);
            }

            if (dict.contains(varName))
            {
                dict.update(varName, value);
            }
            else
            {
                dict.put(varName, value);
            }

        }
        catch (IOException x){
            throw new InterpreterException(x.toString());
        }
        return state;
    }

    @Override
    public String toString(){
        return "ReadFile(" + expFileId + "," + varName + ")";
    }
}
