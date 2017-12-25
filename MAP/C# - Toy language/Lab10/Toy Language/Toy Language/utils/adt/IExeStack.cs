using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Toy_Language.utils
{
    public interface IExeStack<T> : IEnumerable
    {
        List<T> List { get; set; }
        void Push(T e);
        T Pop();
        bool IsEmpty();
    }
}
