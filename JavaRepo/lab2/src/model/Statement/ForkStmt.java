package model.Statement;

import utils.FileData;
import utils.MyStack;
import utils.PrgState;
import utils.adt.*;

public class ForkStmt implements IStmt {
    private IStmt stmt;
    public ForkStmt(IStmt _stmt) {
        stmt = _stmt;
    }

    @Override
    public PrgState execute(PrgState p) {
        // Creating the stack containing only the statement given
        MyIStack<IStmt> stack = new MyStack<>();
        //stack.push(stmt);

        // A deep copy of the symbol table, we don't want to share it between
        MyIDictionary<String, Integer> st = p.getSymTable().copy();

        // The rest are references, *can be shared between processes, we don't want to use same heap addresses
        MyIHeap<Integer, Integer> heap = p.getHeap();
        MyIFileTable<Integer, FileData> fileT = p.getFileTable();
        MyIList<Integer> list = p.getOut();

        PrgState newPrg = new PrgState(stack, st, list, fileT, heap, stmt);
        return newPrg;
    }

    @Override
    public String toString() {
        return "fork(" + stmt + ")";
    }
}
