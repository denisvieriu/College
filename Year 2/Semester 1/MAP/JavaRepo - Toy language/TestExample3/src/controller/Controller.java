package controller;

import repository.*;
import model.*;

import java.util.ArrayList;

public class Controller {
    private IRepository repo;

    public Controller(IRepository _repo){
        repo = _repo;
    }

    public void executeOne(){

        if (!repo.isDone()) {
            Exp exp = repo.getExp();
            System.out.println(exp.toString() + "=" + exp.eval());
        }
    }

    public void addExp(Exp exp){
        repo.addExp(exp);
    }

    public void executeAll(){
        while (!repo.isDone()){
            executeOne();
        }
    }

    public void getAll(){
        for (Exp exp : repo.getList()){
            System.out.println(exp);
        }

    }
}
