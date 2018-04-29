using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Toy_Language.utils
{
    public class IDGenerator
    {
        private static int counter = 1;
        public static int GenerateId() { return counter++; }
    }
}
