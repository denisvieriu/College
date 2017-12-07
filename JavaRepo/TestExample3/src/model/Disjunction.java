package model;

public class Disjunction implements Exp{
    private Exp exp1, exp2;

    public Disjunction(Exp _exp1, Exp _exp2){
        exp1 = _exp1;
        exp2 = _exp2;
    }

    @Override
    public int eval(){
        return exp1.eval() | exp2.eval();
    }

    @Override
    public String toString(){
        return "(" + exp1 + " OR " + exp2 + ")";
    }
}