package controller;

import exceptions.DivideByZeroException;
import exceptions.InvalidOperandException;
import exceptions.NotExistingException;
import model.Statement.IStmt;
import repository.*;
import utils.*;
import utils.adt.MyIStack;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller {
    private MyIRepository repo;
    public Controller(MyIRepository r){
        repo = r;
    }

    public void addPrgState(PrgState prgState){
        repo.addPrgState(prgState);
    }

    private PrgState executeOneStep(PrgState state) throws DivideByZeroException, InvalidOperandException, NotExistingException {
        MyIStack<IStmt> stk = state.getExeStack();
        if(stk.isEmpty())
        {
            throw new NotExistingException("Stack is empty");
        }
        IStmt crtStmt = stk.pop();
        return crtStmt.execute(state);
    }

    public void allStep() throws DivideByZeroException, InvalidOperandException, NotExistingException {
        PrgState prg = repo.getCurrentPrgState();

        try {
            while (true) {
                executeOneStep(prg);
                prg.getHeap().setContent(conservativeGarbageCollector(prg.getSymTable().getContent().values(), prg.getHeap().getContent()));

                System.out.println(prg);
                repo.logPrgStateExec();
            }
        }
        finally {
            prg.getSymTable().setContent(removeRefFromSymTable(prg.getFileTable().getContent().keySet(), prg.getSymTable().getContent()));
            System.out.println("Closed files removed from symbol table");
            System.out.println(prg);
            repo.logPrgStateExec();
        }
    }

    Map<String, Integer> removeRefFromSymTable(Collection<Integer> FileTable, Map<String, Integer> SymTable){
        return SymTable.entrySet().stream()
                .filter(e->FileTable.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    Map<Integer, Integer> conservativeGarbageCollector(Collection<Integer> symTableValues, Map<Integer, Integer> heap){
        return heap.entrySet().stream()
                .filter(e->symTableValues.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void setRepoFileName(String fileName){
        repo.setFileName(fileName);
    }


}
