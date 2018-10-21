package repository;

import exceptions.InterpreterException;
import model.Statement.IStmt;
import utils.FileData;
import utils.adt.MyIDictionary;
import utils.adt.MyIFileTable;
import utils.PrgState;
import utils.adt.MyIHeap;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MyRepository implements MyIRepository {
    private List<PrgState> aL;
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
    public void setPrgList(List<PrgState> l) {
        aL = l;
    }

    @Override
    public List<PrgState> getPrgList() {
        return aL;
    }

    @Override
    public void logPrgStateExec(PrgState state){
        try(PrintWriter log = new PrintWriter(
                new BufferedWriter(new FileWriter(fileName, true)))){
            int idC = state.getId();

            log.println("Current id: [" + idC + "]");
            System.out.println("Current id: [" + idC + "]");

            log.println("ExeStack:");
            System.out.println("ExeStack:");
            for (IStmt st : state.getExeStack().getAll()){
                log.println("\t" + st);
                System.out.println(st);
            }

            System.out.println("SymTable:");
            MyIDictionary<String, Integer> dict = state.getSymTable();
            log.println("SymTable:");
            for (String key : dict.getAll()){
                log.println("\t" + key + "-->" + dict.get(key));
                System.out.println(key + "-->" + dict.get(key));
            }

            System.out.println("Out");
            log.println("Out:");
            for (int v : state.getOut().getAll()){
                log.println("\t" + v);
                System.out.println(v);
            }

            System.out.println("FileTable:");
            log.println("FileTable:");
            MyIFileTable<Integer, FileData> fileTable = state.getFileTable();
            for (int id : fileTable.getAll()){
                log.println("\t" + id + "-->" + fileTable.get(id).getFilename());
                System.out.println(id + "-->" + fileTable.get(id).getFilename());
            }

            System.out.println("Heap:");
            log.println("Heap:");
            MyIHeap<Integer, Integer> heap = state.getHeap();
            for (int address : heap.getAll()) {
                log.println("\t" + address + "-->" + heap.get(address));
                System.out.println(address + "-->" + heap.get(address));
            }

            System.out.println();
            log.println("__________________________________________________________________________________________________");
        }
        catch (IOException x){
            throw new InterpreterException(x.toString());
        }
    }

}
