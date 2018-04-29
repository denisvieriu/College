using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Toy_Language.utils
{
    public class ExeStack<T> : IExeStack<T>, IEnumerable
    {
        private List<T> list;

        public List<T> List { get => this.list; set => this.list = value; }

        public ExeStack()       => this.List = new List<T>();

        public bool IsEmpty()   => (this.List.Count == 0);

        public T Pop()
        {
            T el = this.List[0];
            this.List.RemoveAt(0);
            return el;
        }

        public void Push(T e)   => this.List.Insert(0, e);

        public override String ToString()
        {
            StringBuilder stb = new StringBuilder();

            foreach(T e in this.List)
            {
                stb.Append(e.ToString());
                stb.Append('\n');
            }

            return stb.ToString();
        }

        // Implementation for the GetEnumerator method
        IEnumerator IEnumerable.GetEnumerator()
        {
            return (IEnumerator) GetEnumerator();
        }

        public ExeStackEnum<T> GetEnumerator()
        {
            return new ExeStackEnum<T>(List);
        }
    }

    // When you implement IEnumerable, you must also implement IEnumerator
    public class ExeStackEnum<T> : IEnumerator
    {
        public List<T> list;

        // Enumerators are positionaed before the first element
        // until the first MoveNext() call

        int position = -1;

        public ExeStackEnum(List<T> _list)
        {
            list = _list;
        }

        public bool MoveNext()
        {
            position++;
            return (position < list.Count);
        }

        public void Reset()
        {
            position = -1;
        }

        object IEnumerator.Current
        {
            get
            {
                return Current;
            }
        }

        public T Current
        {
            get
            {
                try
                {
                    return (list[position]);
                }
                catch (IndexOutOfRangeException)
                {
                    throw new InvalidOperationException();
                }
            }
        }
    }
}
