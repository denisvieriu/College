using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Toy_Language.model.expressions;
using Toy_Language.utils;

namespace Toy_Language.model.statement
{
    public class ReadRFile : IStmt
    {
        private Exp expFileId;
        private string varName;

        public ReadRFile(Exp _expFileId, string _varName)
        {
            expFileId = _expFileId;
            varName = _varName;
        }

        public PrgState Execute(PrgState state)
        {
            int value;
            IModDictionary<string, int> symbTable = state.SymbTable;

            // 
            int id = expFileId.Eval(symbTable);

            //
            FileData fd = state.FileTable.Get(id);
            System.IO.StreamReader reader = fd.Header;
            string line = reader.ReadLine();

            if (line == null)
            {
                value = 0;
            }
            else
            {
                value = Int32.Parse(line);
                System.Console.WriteLine(">>>>>>>>>>>>>>> " + value);
            }

            if (symbTable.Contains(varName))
            {
                symbTable.Update(varName, value);
            }
            else
            {
                symbTable.Put(varName, value);
            }


            return state;
        }

        public override string ToString()
        {
            return "ReadFile(" + expFileId + "," + varName + ")";
        }
    }
}
