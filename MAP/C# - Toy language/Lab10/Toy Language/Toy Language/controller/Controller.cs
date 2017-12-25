using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Toy_Language.model.statement;
using Toy_Language.repository;
using Toy_Language.utils;

namespace Toy_Language.controller
{
    public class Controller
    {
        private IRepository repo;

        public Controller(
            IRepository _repo
            )
        {
            repo = _repo;
        }

        public void AddPrgState(PrgState prgState)
        {
            repo.AddPrgState(prgState);
        }

        private PrgState OneStep(PrgState state)
        {
            IExeStack<IStmt> stk = state.ExeStack;
            if (stk.IsEmpty())
            {
                throw new Exception("Empty stack");
            }

            IStmt crtStmt = stk.Pop();
            return crtStmt.Execute(state);
        }

        public void AllStep()
        {
            PrgState prg = repo.GetCurrentPrg();
            try
            {
                while(true)
                {
                    OneStep(prg);
                    repo.LogPrgStateExec();
                    System.Console.WriteLine(prg.ToString());
                }
            }
            catch (System.Exception ex)
            {
                System.Console.WriteLine();
                System.Console.WriteLine(ex);	
            }
        }
    }
}
