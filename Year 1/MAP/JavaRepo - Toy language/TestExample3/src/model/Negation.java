package model;

public class Negation implements Exp{
    private Exp exp;
    public Negation(Exp _exp){
        exp = _exp;
    }

    @Override
    public int eval(){
        return (exp.eval() == 1)?(0):(1);
    }

    @Override
    public String toString(){
        return "NOT("+ exp + ")";
    }
}