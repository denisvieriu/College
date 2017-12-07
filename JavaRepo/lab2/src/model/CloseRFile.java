package model;

import exceptions.DivideByZeroException;
import exceptions.InterpreterException;
import exceptions.InvalidOperandException;
import exceptions.NotExistingException;
import model.Expressions.Exp;
import model.Statement.IStmt;
import utils.FileData;
import utils.adt.MyIDictionary;
import utils.adt.MyIFileTable;
import utils.PrgState;
import utils.adt.MyIHeap;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStmt {
    private Exp expFileId;

    public CloseRFile(Exp _expFileId){
        expFileId = _expFileId;
    }

    @Override
    public PrgState execute(PrgState state) throws DivideByZeroException, InvalidOperandException, NotExistingException {
        MyIDictionary<String, Integer> dict = state.getSymTable();
        MyIFileTable<Integer, FileData> fileTable = state.getFileTable();
        MyIHeap<Integer, Integer> heap = state.getHeap();
        int id = expFileId.eval(dict, heap);
        FileData fd = fileTable.get(id);

        try{
            BufferedReader br = fd.getHeader();
            br.close();
            fileTable.remove(id);
        }
        catch (IOException x)
        {
            throw new InterpreterException(x.toString());
        }

        return state;
    }

    @Override
    public String toString(){
        return "CloseFile(" + expFileId + ")";
    }
}
