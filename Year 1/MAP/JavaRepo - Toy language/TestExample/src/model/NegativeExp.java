package model;

public class NegativeExp implements Exp {
    private Exp exp;

    public NegativeExp(Exp _e){
        exp = _e;
    }

    @Override
    public String toString(){
        return "" + exp;
    }

    @Override
    public int eval(){
        return exp.eval() * -1;
    }
}
