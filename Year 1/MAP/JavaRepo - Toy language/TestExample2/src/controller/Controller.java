package controller;

import repository.IRepository;
import model.*;

public class Controller {
    private IRepository repo;

    public Controller(IRepository _repo){
        repo = _repo;
    }

    public void addExp(Exp exp){
        repo.addExp(exp);
    }

    public void executeOne(Exp exp){
        System.out.println(exp.eval());
    }

    public void executeAll(){
        while (repo.notFull()){
            executeOne(repo.getExp());
        }
    }
}
