using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Toy_Language.utils;

namespace Toy_Language.model.statement
{
    public class CompStmt : IStmt
    {
        private IStmt first;
        private IStmt second;

        public CompStmt(
            IStmt _first,
            IStmt _second
            )
        {
            first = _first;
            second = _second;
        }

        public PrgState Execute(PrgState state)
        {
            IExeStack<IStmt> exeStack = state.ExeStack;
            exeStack.Push(first);
            exeStack.Push(second);

            return state;
        }

        public override string ToString()
        {
            return "(" + first + ";" + second + ")";
        }


    }
}
