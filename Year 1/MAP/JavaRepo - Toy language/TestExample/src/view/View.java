package view;

import controller.Controller;
import model.*;
import repository.Repository;

public class View {
    public static void main(String[] args) {
        Repository repo = new Repository();
        Controller ctrl = new Controller(repo);
        Exp exp1 = new MaxExp(new ConstExp(5), new ConstExp(8));
        Exp exp2 = new MinExp(new ConstExp(6), new ConstExp(-3));
        Exp exp3 = new NegativeExp(new ConstExp(-4));
        ctrl.addExp(exp1);
        ctrl.addExp(exp2);
        ctrl.addExp(exp3);
        ctrl.executeAll();

    }
}
