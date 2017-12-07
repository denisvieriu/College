package utils;

import model.Statement.IStmt;
import utils.adt.*;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String,Integer> symTable;
    private MyIList<Integer> out;
    private IStmt originalProgram;
    private MyIFileTable<Integer, FileData> fileTable;
    private MyIHeap<Integer, Integer> heap;

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String,Integer> symtbl,
                    MyIList<Integer> ot, MyIFileTable<Integer, FileData> fileT, MyIHeap<Integer, Integer> hp, IStmt prg){
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        fileTable = fileT;
        heap = hp;
        originalProgram = prg;
        stk.push(prg);
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public MyIDictionary<String, Integer> getSymTable() {
        return symTable;
    }

    public void setSymTable(MyIDictionary<String, Integer> symTable) {
        this.symTable = symTable;
    }

    public MyIList<Integer> getOut() {
        return out;
    }

    public void setOut(MyIList<Integer> out) {
        this.out = out;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public void setOriginalProgram(IStmt originalProgram) { this.originalProgram = originalProgram; }

    public MyIFileTable<Integer, FileData> getFileTable() { return fileTable; }

    public void setFileTable(MyIFileTable<Integer, FileData> fileTable) { this.fileTable = fileTable; }

    public MyIHeap<Integer, Integer> getHeap() { return heap; }

    public void setHeap(MyIHeap<Integer, Integer> heap) {this.heap = heap; }

    @Override
    public String toString(){
        StringBuffer str = new StringBuffer();
        str.append("ExeStack:\n" + exeStack + '\n' + "SymbolTable: " + symTable + '\n' + "ResultList: " + out + '\n' + "Heap: " + heap + '\n' + "OriginalProgram: " + originalProgram + '\n');
        return str.toString();
    }

}
