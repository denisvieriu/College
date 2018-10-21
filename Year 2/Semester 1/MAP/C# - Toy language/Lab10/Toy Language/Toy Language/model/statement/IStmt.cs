using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Toy_Language.utils;

namespace Toy_Language.model.statement
{
    public interface IStmt
    {
        PrgState Execute(PrgState state);
    }
}
