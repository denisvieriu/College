package model;

public class MaxExp implements Exp {
    private Exp exp1, exp2;

    public MaxExp(Exp _e1, Exp _e2){
        exp1 = _e1;
        exp2 = _e2;
    }

    @Override
    public int eval(){
        int eval1 = exp1.eval();
        int eval2 = exp2.eval();

        return (eval1 > eval2)?(eval1):(eval2);
    }

    @Override
    public String toString(){
        return "max(" + exp1 + "," + exp2 + ")";
    }

}
