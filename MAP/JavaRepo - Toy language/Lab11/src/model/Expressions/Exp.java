package model.Expressions;

import exceptions.DivideByZeroException;
import exceptions.InvalidOperandException;
import exceptions.NotExistingException;
import utils.adt.MyIDictionary;
import utils.adt.MyIHeap;

public abstract class Exp {
    public abstract int eval(MyIDictionary<String, Integer> symboTable,
                             MyIHeap<Integer, Integer> heap) throws DivideByZeroException, InvalidOperandException, NotExistingException;
    public abstract String toString();
}

