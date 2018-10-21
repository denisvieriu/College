package model;

public class ConstExp implements Exp{
    private int nr;

    public ConstExp(int _nr){
        nr = _nr;
    }

    @Override
    public int eval(){
        return nr;
    }

    @Override
    public String toString(){
        return "" + nr;
    }
}