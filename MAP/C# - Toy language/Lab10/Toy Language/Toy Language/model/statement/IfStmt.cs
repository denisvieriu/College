using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Toy_Language.model.expressions;
using Toy_Language.utils;

namespace Toy_Language.model.statement
{
    public class IfStmt : IStmt
    {
        private Exp exp;
        private IStmt thenS, elseS;

        public IfStmt(
            Exp _exp,
            IStmt _stmt1, 
            IStmt _stmt2
            )
        {
            this.exp = _exp;
            this.thenS = _stmt1;
            this.elseS = _stmt2;
        }

        public PrgState Execute(PrgState state)
        {
            IModDictionary<string, int> symbTable = state.SymbTable;
            IExeStack<IStmt> exeStack = state.ExeStack;

            int res = exp.Eval(symbTable);
            
            if (res != 0)
            {
                exeStack.Push(thenS);
            }
            else
            {
                exeStack.Push(elseS);
            }

            return state;
        }

        public override string ToString()
        {
            return "IF(" + exp.ToString() + ")THEN(" + thenS.ToString() + ")ELSE(" + elseS.ToString() + ")";
        }
    }
}
