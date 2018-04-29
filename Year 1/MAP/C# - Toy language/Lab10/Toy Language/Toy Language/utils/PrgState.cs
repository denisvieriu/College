using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Toy_Language.model.statement;
using Toy_Language.utils.adt;

namespace Toy_Language.utils
{
    public class PrgState
    {
        private IExeStack<IStmt> exeStack;
        private IModDictionary<string, int> symbTable;
        private IModList<int> outList;
        private IStmt originalProgram;
        private IFileTable<int, FileData> fileTable;

        public PrgState(
            IExeStack<IStmt> _exeStack,
            IModDictionary<string, int> _symbTable,
            IModList<int> _outList,
            IFileTable<int, FileData> _fileTable,
            IStmt _originalProgram
            )
        {
            this.ExeStack = _exeStack;
            this.SymbTable = _symbTable;
            this.OutList = _outList;
            this.FileTable = _fileTable;
            this.OriginalProgram = _originalProgram;
            ExeStack.Push(OriginalProgram);
        }

        public IExeStack<IStmt> ExeStack { get => exeStack; set => exeStack = value; }
        public IModDictionary<string, int> SymbTable { get => symbTable; set => symbTable = value; }
        public IModList<int> OutList { get => outList; set => outList = value; }
        public IStmt OriginalProgram { get => originalProgram; set => originalProgram = value; }
        public IFileTable<int, FileData> FileTable { get => fileTable; set => fileTable = value; }

        public override string ToString()
        {
            StringBuilder str = new StringBuilder();
            str.Append("ExeStack:\n" + ExeStack + '\n' + "SymbolTable: "  + SymbTable + '\n' + "ResultList: " + OutList + '\n' + "OriginalProgram" + OriginalProgram);
            return str.ToString();
        }

    }
}
