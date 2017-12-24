using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Toy_Language.utils
{
    public interface IModList<T>
    {
        List<T> List { get; set; }
        void Add(T el);
    }
}
