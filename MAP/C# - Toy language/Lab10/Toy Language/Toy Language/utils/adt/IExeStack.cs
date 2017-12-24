using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Toy_Language.utils
{
    public interface IExeStack<T>
    {
        Queue<T> Queue { get; set; }
        void Push(T e);
        T Pop();
        bool IsEmpty();
    }
}
