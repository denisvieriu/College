using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Toy_Language.utils;

namespace Toy_Language.model.expressions
{
    public class ArithExp : Exp
    {
        private Exp e1, e2;
        private char op;

        public ArithExp(char _op, Exp _e1, Exp _e2)
        {
            this.op = _op;
            this.e1 = _e1;
            this.e2 = _e2;
        }

        public override int Eval(IModDictionary<string, int> symbTable)
        {
            int leftExp = e1.Eval(symbTable);
            int rightExp = e2.Eval(symbTable);

            switch (op)
            {
                case '+':
                    return leftExp + rightExp;
                case '-':
                    return leftExp - rightExp;
                case '*':
                    return leftExp * rightExp;
                case '/':
                    if (rightExp == 0)
                    {
                        throw new Exception("Create new DivideZeroException class!!");
                    }
                    else
                    {
                        return leftExp / rightExp;
                    }
                default:
                    throw new Exception("Invalid op");
            }
        }

        public override string ToString()
        {
            return "" + e1 + op + e2;
        }
    }
}
