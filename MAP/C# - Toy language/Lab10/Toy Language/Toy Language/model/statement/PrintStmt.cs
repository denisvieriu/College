using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Toy_Language.model.expressions;
using Toy_Language.utils;

namespace Toy_Language.model.statement
{
    public class PrintStmt : IStmt
    {
        private Exp exp;

        public PrintStmt(
            Exp _exp
            )
        {
            exp = _exp;
        }

        public PrgState Execute(PrgState state)
        {
            IModList<int> list = state.OutList;
            IModDictionary<string, int> dict = state.SymbTable;

            int res = exp.Eval(dict);
            list.Add(res);

            return state;
        }

        public override string ToString()
        {
            return "print(" + exp.ToString() + ")";
        }

    }
}
