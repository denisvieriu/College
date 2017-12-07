package model.Statement;

import exceptions.DivideByZeroException;
import exceptions.InvalidOperandException;
import exceptions.NotExistingException;
import utils.PrgState;

public interface IStmt {
    public String toString();
    public PrgState execute(PrgState state) throws DivideByZeroException, InvalidOperandException, NotExistingException;
}


