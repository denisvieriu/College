package model.Expressions;

import exceptions.DivideByZeroException;
import exceptions.InvalidOperandException;
import exceptions.NotExistingException;
import utils.adt.MyIDictionary;
import utils.adt.MyIHeap;

public class ArithExp extends Exp {
    private Exp e1;
    private Exp e2;
    private char op;

    public ArithExp(char _op, Exp _e1, Exp _e2)
    {
        op = _op;
        e1 = _e1;
        e2 = _e2;
    }

    @Override
    public int eval(MyIDictionary<String, Integer> tbl,
                    MyIHeap<Integer, Integer> heap) throws DivideByZeroException, InvalidOperandException, NotExistingException {
        int leftExp = e1.eval(tbl, heap);
        int rightExp = e2.eval(tbl, heap);
        switch (op)
        {
            case '+': // +
                return leftExp + rightExp;
            case '-': // -
                return leftExp - rightExp;
            case '*': // *
                return leftExp * rightExp;
            case '/':
                if (rightExp == 0)
                {
                    throw new DivideByZeroException();
                }
                else
                {
                    return leftExp / rightExp;
                }
            default:
                throw new InvalidOperandException();
        }
    }

    @Override
    public String toString()
    {
        return "" + e1 + op + e2;
    }
}
