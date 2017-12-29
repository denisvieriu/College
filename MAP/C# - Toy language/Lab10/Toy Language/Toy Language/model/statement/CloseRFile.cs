using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Toy_Language.model.expressions;
using Toy_Language.utils;
using Toy_Language.utils.adt;

namespace Toy_Language.model.statement
{
    public class CloseRFile : IStmt
    {
        private Exp expFileId;

        public CloseRFile(Exp _expFileId)
        {
            expFileId = _expFileId;
        }

        public PrgState Execute(PrgState state)
        {
            IModDictionary<string, int> symbTable = state.SymbTable;
            int id = expFileId.Eval(symbTable);

            FileData fd = state.FileTable.Get(id);
            System.IO.StreamReader reader = fd.Header;
            reader.Close();
            state.FileTable.Remove(id);

            return state;
        }

        public override string ToString()
        {
            return "CloseFile(" + expFileId + ")";
        }
    }
}
