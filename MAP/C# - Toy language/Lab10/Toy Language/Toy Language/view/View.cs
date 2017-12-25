using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Toy_Language.controller;
using Toy_Language.model.expressions;
using Toy_Language.model.statement;
using Toy_Language.utils;
using Toy_Language.utils.adt;

namespace Toy_Language.view
{
    public class View
    {
        private Controller ctrl;

        public View(Controller ctrl)
        {
            this.ctrl = ctrl;
        }

        private void MainMenu()
        {
            System.Console.WriteLine("Main menu");
            System.Console.WriteLine("Here are some hard-coded examples: ");

            System.Console.WriteLine("1 - Example 1:");
            System.Console.WriteLine("\tv=2;Print(v)");

            System.Console.WriteLine("2 - Example 2:");
            System.Console.WriteLine("\ta=2+3*5;b=a+1;Print(b)");
        }

        private void ExecuteCommand(int command)
        {
            IExeStack<IStmt> exeStack = new ExeStack<IStmt>();
            IModList<int> outList = new ModList<int>();
            IModDictionary<string, int> dict = new ModDictionary<string, int>();
            IStmt stmt = default(IStmt);
            IFileTable<int, FileData> fileTable = new FileTable<int, FileData>();

            switch (command)
            {
                case 1:
                    stmt = new CompStmt(
                        new AssingStmt("v", new ConstExp(2)),
                        new PrintStmt(new VarExp("v")));
                    break;
                case 2:
                    break;
                default:
                    // throw some error
                    throw new Exception("Invalid command");
            }

            PrgState prgState = new PrgState(exeStack, dict, outList, fileTable, stmt);

            ctrl.AddPrgState(prgState);
            try
            {
                ctrl.AllStep();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex);
            }
            
        }

        public void ExecuteMainMenu()
        {
            string input;
            int command;
            this.MainMenu();

            Console.WriteLine("Enter your command: ");
            input = Console.ReadLine();
            if (!Int32.TryParse(input, out command))
            {
                // not a number..
            }
            else
            {
                try
                {
                    this.ExecuteCommand(command);
                }
                catch (System.Exception ex)
                {
                    Console.WriteLine(ex);
                }
            }

        }
    }
}
