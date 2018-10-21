using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Toy_Language.utils
{
    public class FileData
    {
        private string fileName;
        private StreamReader header;

        public FileData(string _fileName, StreamReader _header)
        {
            FileName = _fileName;
            Header = _header;
        }

        public string FileName { get => fileName; set => fileName = value; }
        public StreamReader Header { get => header; set => header = value; }
            
        public override string ToString()
        {
            return fileName;
        }
    }
}
