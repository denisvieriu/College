package view;

import model.*;
import repository.*;
import controller.*;

public class Main {
    public static void main(String []argv){
        Exp exp1 = new AbsExp(new ConstExp(-3));
        Exp exp2 = new IfnegExp(new ConstExp(-3 ), new ConstExp(-2)); // -2
        Exp exp3 = new IfposExp(new ConstExp(-3 ), new ConstExp(-2)); // 3
        IRepository repo = new Repository();
        Controller ctrl = new Controller(repo);

        ctrl.addExp(exp1);
        ctrl.addExp(exp2);
        ctrl.addExp(exp3);

        ctrl.executeAll();
    }
}
