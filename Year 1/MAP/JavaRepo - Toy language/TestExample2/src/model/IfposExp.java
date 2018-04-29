package model;

public class IfposExp extends Exp {
    private Exp exp1, exp2;

    public IfposExp(Exp _exp1, Exp _exp2){
        exp1 = _exp1;
        exp2 = _exp2;
    }

    @Override
    public int eval(){
        int r1 = exp1.eval();
        int r2 = exp2.eval();
        if (r1 >= 0){
            return r2;
        }

        AbsExp absE = new AbsExp(exp1);
        return absE.eval();
    }

    @Override
    public String toString(){
        return "" + exp1 + " " + exp2;
    }

}
