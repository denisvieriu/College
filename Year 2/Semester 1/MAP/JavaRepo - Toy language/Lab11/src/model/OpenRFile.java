package model;

import exceptions.InterpreterException;
import model.Statement.IStmt;
import utils.FileData;
import utils.IDGenerator;
import utils.adt.MyIDictionary;
import utils.PrgState;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFile implements IStmt {
    private String varFileId;
    private String fileName;

    public OpenRFile(String _varFileId, String _fileName){
        varFileId = _varFileId;
        fileName =  _fileName;
    }

    public boolean isOpen(PrgState prg){
        for (FileData crt : prg.getFileTable().getValues()){
            if (crt.getFilename().equals(fileName)){
                return true;
            }
        }
        return false;
    }
    @Override
    public PrgState execute(PrgState state) {
        if (isOpen(state)) {
            throw new InterpreterException("File " + fileName + "is open");
        } else {
            try {
                BufferedReader br = new BufferedReader(new FileReader(fileName));
                FileData fd = new FileData(fileName, br);
                int id = IDGenerator.generate_id();

                // add the id and descriptor
                state.getFileTable().add(id, fd);

                // get the dictionary
                MyIDictionary<String, Integer> dict = state.getSymTable();
                if (dict.contains(varFileId)) {
                    dict.update(varFileId, id);
                } else {
                    dict.put(varFileId, id);
                }
            }
            catch (IOException ex) {
                throw new InterpreterException(ex.toString());
            }
            return null;
        }
    }

    @Override
    public String toString(){
        return "OpenFile(" + fileName + " " + varFileId + ")";
    }
}
