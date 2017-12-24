using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Toy_Language.utils
{
    public class ModList<T> : IModList<T>
    {
        private List<T> list;

        public List<T> List { get => list; set => list = value; }

        public ModList()
        {
            list = new List<T>();
        }

        public void Add(T el)
        {
            list.Add(el);
        }

        public override String ToString()
        {
            StringBuilder stb = new StringBuilder();

            foreach (T e in list)
            {
                stb.Append(e.ToString());
                stb.Append('\n');
            }

            return stb.ToString();
        }
    }
}
