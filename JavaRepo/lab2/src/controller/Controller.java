package controller;

import exceptions.DivideByZeroException;
import exceptions.InterpreterException;
import exceptions.InvalidOperandException;
import exceptions.NotExistingException;
import model.Statement.IStmt;
import repository.*;
import utils.*;
import utils.adt.MyIStack;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private MyIRepository repo;
    ExecutorService executor;

    public Controller(MyIRepository r){
        repo = r;
    }

    public void addPrgState(PrgState prgState){
        repo.addPrgState(prgState);
    }

    public List<PrgState> removeCompltedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(l->l.isNotCompleted())
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException {
        prgList.forEach(prg->repo.logPrgStateExec(prg));

        //RUN concurrently one step for each of the existing PrgStates

        List<Callable<PrgState>> callList;
        callList = prgList.stream()
                .map((PrgState p)->(Callable<PrgState>)(()->{return p.executeOneStep();}))
                .collect(Collectors.toList());

        List<PrgState> lp = executor.invokeAll(callList).stream()
                .map(future->{
                    try{
                        return future.get();
                    }
                    catch (InterruptedException e) {
                        throw new InterpreterException("Some thread error?...");
                    }
                    catch (ExecutionException e) {
                        throw new InterpreterException("Some thread error?...");
                    }
                })
                .filter(p->p!=null)
                .collect(Collectors.toList());

        prgList.addAll(lp);
        repo.setPrgList(prgList);
    }

    public void allStep() throws InterruptedException {
        executor = Executors.newFixedThreadPool(3) ;
        List<PrgState> prg = removeCompltedPrg(repo.getPrgList());

        while (prg.size() > 0) {
            oneStepForAllPrg(prg);
            prg = removeCompltedPrg(repo.getPrgList());
        }

        executor.shutdown();
        repo.setPrgList(prg);
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
