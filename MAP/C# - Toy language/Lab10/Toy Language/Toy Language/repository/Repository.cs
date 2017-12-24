using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Toy_Language.utils;

namespace Toy_Language.repository
{
    public class Repository : IRepository
    {
        private List<PrgState> list;
        
        public Repository()
        {
            list = new List<PrgState>();
        }

        public void AddPrgState(PrgState p)
        {
            list.Add(p);
        }

        public PrgState GetCurrentPrg()
        {
            return list[0];
        }
    }
}
