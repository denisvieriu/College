package controller;

import model.Exp;
import repository.IRepository;
import repository.Repository;

public class Controller {
    private Repository repo;

    public Controller(Repository _repo){
        repo = _repo;
    }

    public void addExp(Exp exp){
        repo.addExp(exp);
    }

    public void executeOne(Exp exp){
        System.out.println(exp.toString() + "=" + exp.eval());
    }

    public void executeAll(){
        while(this.repo.notFull()){
            executeOne(repo.getExp());
        }
    }
}
