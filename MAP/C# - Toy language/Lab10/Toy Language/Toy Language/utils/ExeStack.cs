using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Toy_Language.utils
{
    public class ExeStack<T> : IExeStack<T>
    {
        private Queue<T> queue;

        public Queue<T> Queue { get => this.queue; set => this.queue = value; }

        public ExeStack()       => this.queue = new Queue<T>();

        public bool IsEmpty()   => (this.queue.Count == 0);

        public T Pop()          => (this.queue.Dequeue());

        public void Push(T e)   => this.queue.Enqueue(e);

        public override String ToString()
        {
            StringBuilder stb = new StringBuilder();

            foreach(T e in queue)
            {
                stb.Append(e.ToString());
                stb.Append('\n');
            }

            return stb.ToString();
        }
    }
}
