package repository;

import utils.PrgState;

public interface MyIRepository {
    public void addPrgState(PrgState p);
    public PrgState getCurrentPrgState();
    public void logPrgStateExec();
    public void setFileName(String _fileName);
}

