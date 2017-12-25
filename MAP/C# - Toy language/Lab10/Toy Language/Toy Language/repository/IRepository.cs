using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Toy_Language.utils;

namespace Toy_Language.repository
{
    public interface IRepository
    {
        void AddPrgState(PrgState p);
        PrgState GetCurrentPrg();
        void LogPrgStateExec();
    }
}
