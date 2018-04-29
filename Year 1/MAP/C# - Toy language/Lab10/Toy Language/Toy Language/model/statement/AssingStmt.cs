using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Toy_Language.model.expressions;
using Toy_Language.utils;

namespace Toy_Language.model.statement
{
    public class AssingStmt : IStmt
    {
        private string id;
        private Exp exp;

        public AssingStmt(String _id, Exp _exp)
        {
            id = _id;
            exp = _exp;
        }

        public PrgState Execute(PrgState state)
        {
            IModDictionary<string, int> symbTable = state.SymbTable;
            int val = exp.Eval(symbTable);

            if (symbTable.Contains(id))
            {
                symbTable.Update(id, val);
            }
            else
            {
                symbTable.Put(id, val);
            }

            return state;
        }

        public override string ToString()
        {
            return id + "=" + exp.ToString();
        }


    }
}
