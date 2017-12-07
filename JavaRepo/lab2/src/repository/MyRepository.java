package repository;

import exceptions.InterpreterException;
import model.Statement.IStmt;
import utils.FileData;
import utils.adt.MyIDictionary;
import utils.adt.MyIFileTable;
import utils.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MyRepository implements MyIRepository {
    private ArrayList<PrgState> aL;
    private String fileName;

    public MyRepository() {
        aL = new ArrayList<>();
        fileName = "Input";
    }

    @Override
    public void setFileName(String _fileName){
        fileName = _fileName;
    }

    @Override
    public void addPrgState(PrgState p)
    {
        aL.add(p);
    }

    @Override
    public PrgState getCurrentPrgState()
    {
        return aL.get(0);
    }

    @Override
    public void logPrgStateExec(){
        PrgState state = getCurrentPrgState();
        try(PrintWriter log = new PrintWriter(
                new BufferedWriter(new FileWriter(fileName, true)))){
            log.println("ExeStack:");
            for (IStmt st : state.getExeStack().getAll()){
                log.println(st);
            }

            MyIDictionary<String, Integer> dict = state.getSymTable();
            log.println("SymTable:");
            for (String key : dict.getAll()){
                log.println(key + "-->" + dict.get(key));
            }

            log.println("Out:");
            for (int v : state.getOut().getAll()){
                log.println(v);
            }

            log.println("FileTable:");
            MyIFileTable<Integer, FileData> fileTable = state.getFileTable();
            for (int id : fileTable.getAll()){
                log.println(id + "-->" + fileTable.get(id).getFilename());
            }

            log.println();
        }
        catch (IOException x){
            throw new InterpreterException(x.toString());
        }
    }

}
