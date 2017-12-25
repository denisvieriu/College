using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Toy_Language.utils;

namespace Toy_Language.model.statement
{
    public class OpenRFile : IStmt
    {
        private string varFileId;
        private string fileName;

        public OpenRFile(string _varFileId, string _fileName)
        {
            varFileId = _varFileId;
            fileName = _fileName;
        }

        public bool IsOpen(PrgState prg)
        {
            foreach (FileData crt in prg.FileTable.GetAll().Values)
            {
                if(crt.FileName.Equals(fileName))
                {
                    return true;
                }
            }
            return false;
        }

        public PrgState Execute(PrgState state)
        {
            if (IsOpen(state))
            {
                throw new Exception("File " + fileName + " is open");
            }
            else
            {
                System.IO.StreamReader file = new System.IO.StreamReader(fileName);
                FileData fd = new FileData(fileName, file);
                int id = IDGenerator.GenerateId();

                // add the id and the descriptor
                state.FileTable.Add(id, fd);

                // get the dictionary
                IModDictionary<string, int> dict = state.SymbTable;
                if (dict.Contains(varFileId))
                {
                    dict.Update(varFileId, id);
                }
                else
                {
                    dict.Put(varFileId, id);
                }

                return state;
            }
        }
    }
}
