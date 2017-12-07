package model;

public class ConstExp extends Exp {
    private int number;
    public ConstExp(int _number){
        number = _number;
    }

    @Override
    public int eval(){
        return number;
    }

    @Override
    public String toString(){
        return "" + number;
    }
}
