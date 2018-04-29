package model.Expressions;

import utils.adt.MyIDictionary;
import utils.adt.MyIHeap;

public class ConstExp extends Exp {
    private int number;
    public ConstExp(int _number)
    {
        number = _number;
    }

    @Override
    public int eval(MyIDictionary<String, Integer> tbl,
                    MyIHeap<Integer, Integer> heap) {
        return number;
    }

    @Override
    public String toString()
    {
        return "" + number;
    }
}
