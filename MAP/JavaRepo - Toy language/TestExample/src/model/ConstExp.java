package model;

public class ConstExp implements Exp {
    private int number;

    public ConstExp(int _number){
        number =_number;
    }

    @Override
    public String toString(){
        return Integer.toString(number);
    }

    @Override
    public int eval(){
        return number;
    }
}
