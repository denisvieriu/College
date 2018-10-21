using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Toy_Language.utils;

namespace Toy_Language.model.expressions
{
    public abstract class Exp
    {
        public abstract int Eval(IModDictionary<string, int> symbTable);
    }
}
