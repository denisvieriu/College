using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Toy_Language.utils;

namespace Toy_Language.model.expressions
{
    public class ConstExp : Exp
    {
        private int number;
        
        public ConstExp(int _number)
        {
            this.number = _number;
        }

        public override int Eval(IModDictionary<string, int> symbTable)
        {
            return number;
        }

        public override string ToString()
        {
            return "" + number;
        }
    }
}
