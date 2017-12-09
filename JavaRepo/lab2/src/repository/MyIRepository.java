package repository;

import utils.PrgState;

import java.util.List;

public interface MyIRepository {
    public void addPrgState(PrgState p);
    // Lab Assign. 6
    public void setPrgList(List<PrgState> l);
    public List<PrgState> getPrgList();

    public void logPrgStateExec(PrgState p);
    public void setFileName(String _fileName);
}

