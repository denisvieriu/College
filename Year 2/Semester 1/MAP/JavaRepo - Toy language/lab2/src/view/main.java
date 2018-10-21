package view;

import controller.Controller;
import model.*;
import repository.MyIRepository;
import repository.MyRepository;
import utils.*;


public class main {
    public static void main(String[] args) {

        MyIRepository repo =  new MyRepository();
        Controller ctrl = new Controller(repo);
        View view = new View(ctrl);
        view.executeMainMenu();
        /*try {
            IStmt stmt = new CompStmt(new AssignStmt("v", new ConstExp(2)), new PrintStmt(new VarExp("v")));
            MyStack<IStmt> exec = new MyStack<>();

            MyDictionary<String, Integer> symbolT = new MyDictionary<>();
            MyList<Integer> msg = new MyList<>();
            PrgState prg = new PrgState(exec, symbolT, msg, stmt);

            MyRepository repo = new MyRepository();
            Controller ctrl = new Controller(repo);
            repo.addPrgState(prg);
            ctrl.allStep();
        }
        catch (Exception e){

        }*/
    }

}
