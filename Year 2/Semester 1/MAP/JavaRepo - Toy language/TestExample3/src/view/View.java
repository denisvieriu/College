package view;

import model.*;
import repository.*;
import controller.*;

public class View {
    public static void main(String []argv){
        Exp exp1 = new Disjunction(new ConstExp(0), new ConstExp(1));
        Exp exp2 = new Conjunction(new ConstExp(0), new ConstExp(0));
        Exp exp3 = new Negation(new ConstExp(1));
        Exp exp4 = new Conjunction(exp1, new Disjunction(new ConstExp(1), new ConstExp(1)));
        Exp exp5 = new Negation(new Conjunction(exp1, new Disjunction(new ConstExp(0), new ConstExp(0))));

        IRepository repo = new Repository();
        Controller ctrl = new Controller(repo);
        ctrl.addExp(exp1);
        ctrl.addExp(exp2);
        ctrl.addExp(exp3);
        ctrl.addExp(exp4);
        ctrl.addExp(exp5);

        ctrl.executeAll();
    }

}
