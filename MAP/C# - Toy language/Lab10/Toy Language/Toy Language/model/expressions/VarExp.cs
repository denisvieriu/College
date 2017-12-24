using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Toy_Language.utils;

namespace Toy_Language.model.expressions
{
    public class VarExp : Exp
    {
        private string id;

        public VarExp(string _id)
        {
            this.id = _id;
        }

        public override int Eval(IModDictionary<string, int> symbTable)
        {
            if (symbTable.Contains(id))
            {
                return symbTable.Get(id);
            }

            throw new Exception("Element not found");
        }

        public override string ToString()
        {
            return id;
        }
    }
}
