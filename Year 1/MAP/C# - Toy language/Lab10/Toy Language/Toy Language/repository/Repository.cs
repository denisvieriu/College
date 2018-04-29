using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Toy_Language.model.statement;
using Toy_Language.utils;

namespace Toy_Language.repository
{
    public class Repository : IRepository
    {
        private List<PrgState> list;
        private string FileName { get; set; }
        
        public Repository()
        {
            list = new List<PrgState>();
            FileName = "Input2.txt";
        }

        public void AddPrgState(PrgState p)
        {
            list.Add(p);
        }

        public PrgState GetCurrentPrg()
        {
            return list[0];
        }

        public void LogPrgStateExec()
        {
            string del = new string('_', 20);
            PrgState state = this.GetCurrentPrg();

            using (System.IO.StreamWriter file =
                new System.IO.StreamWriter(@"E:\GitRepoCollege\MAP\C# - Toy language\Lab10\Toy Language\Toy Language\" + FileName, true))
            {
                file.WriteLine("Exestack:");
                // IEnumerable, IEnumerator implemented..
                foreach (IStmt st in state.ExeStack)
                {
                    file.WriteLine("\t" + st);
                }

                file.WriteLine("SymbTable:");
                foreach (KeyValuePair<string, int> entry in state.SymbTable.GetAll())
                {
                    file.WriteLine("\t" + entry.Key + "-->" + entry.Value);
                }

                file.WriteLine("Out:");
                foreach (int v in state.OutList.GetAll())
                {
                    file.WriteLine("\t" + v);
                }

                //file.WriteLine("FileTable:");
                //for (int id in state.FileTable.GetAll())
                //{
                //}
                file.WriteLine(del);
            }
        }
    }
}
