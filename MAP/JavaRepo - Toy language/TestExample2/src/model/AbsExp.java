package model;

public class AbsExp extends Exp {
    private Exp exp;

    public AbsExp(Exp _exp){
        exp = _exp;
    }

    @Override
    public int eval(){
        int r = exp.eval();
        return (r < 0)?(r * -1):(r);
    }

    @Override
    public String toString(){
        return "" + exp;
    }
}
