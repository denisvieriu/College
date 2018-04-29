package model;

public class MinExp implements Exp {
    private Exp exp1, exp2;

    public MinExp(Exp _exp1, Exp _exp2){
        exp1 = _exp1;
        exp2 = _exp2;
    }

    @Override
    public int eval(){
        int eval1 = exp1.eval();
        int eval2 = exp2.eval();

        return (eval1 < eval2)?(eval1):(eval2);
    }

    @Override
    public String toString(){
        return "min(" + exp1 + "," + exp2 + ")";
    }
}
