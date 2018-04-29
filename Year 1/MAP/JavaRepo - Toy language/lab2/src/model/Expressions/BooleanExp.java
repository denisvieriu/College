package model.Expressions;

import exceptions.DivideByZeroException;
import exceptions.InvalidOperandException;
import exceptions.NotExistingException;
import utils.adt.MyIDictionary;
import utils.adt.MyIHeap;

public class BooleanExp extends Exp{
    private Exp exp1, exp2;
    private String op;

    public BooleanExp(String _op, Exp _exp1, Exp _exp2){
        op = _op;
        exp1 = _exp1;
        exp2 = _exp2;
    }

    @Override
    public int eval(MyIDictionary<String, Integer> symTable,
                    MyIHeap<Integer, Integer> heap) throws DivideByZeroException, InvalidOperandException, NotExistingException {
        int r1 = exp1.eval(symTable, heap);
        int r2  =exp2.eval(symTable, heap);

        switch (op){
            case "<":
                return (r1 < r2)  ? (1) : (0);
            case "<=":
                return (r1 <= r2) ? (1) : (0);
            case "==":
                return (r1 == r2) ? (1) : (0);
            case "!=":
                return (r1 != r2) ? (1) : (0);
            case ">":
                return (r1 > r2)  ? (1) : (0);
            case ">=":
                return (r1 >= r2) ? (1) : (0);
            default:
                throw new InvalidOperandException("Invalid operand given!");
        }
    }

    @Override
    public String toString(){
        return "" + exp1 + op + exp2;
    }


}
