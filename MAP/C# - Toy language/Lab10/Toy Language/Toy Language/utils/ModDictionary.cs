using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Toy_Language.utils
{
    public class ModDictionary<Key, Value> : IModDictionary<Key, Value>
    {
        private IDictionary<Key, Value> dict;

        public IDictionary<Key, Value> Dict { get => this.dict; set => this.dict = value; }

        public ModDictionary()
        {
            this.dict = new Dictionary<Key, Value>();
        }

        public Value Get(Key key)
        {
            bool keyFound;
            Value    val;

            keyFound = false;
            val      = default(Value); // (default value for a class is null)
            try
            {
                keyFound = this.dict.TryGetValue(key, out val);
            }
            catch (ArgumentNullException e)
            {
                System.Console.WriteLine("Empty key(null) was passed as argument\n" + "Exception found" + e);
            }

            if (!keyFound)
            {
                // throw some Exception (Key was not found)
                throw new Exception("Key not found");
            }
            else
            {
                return val;
            }
        }

        public bool Contains(Key key)
        {
            return this.dict.ContainsKey(key);
        }

        public void Put(Key key, Value val)
        {
            this.dict.Add(key, val);
        }

        public void Update(Key key, Value val)
        {
            // To do: handle exception
            this.dict[key] = val;
        }

        public override string ToString()
        {
            StringBuilder stb = new StringBuilder();

            foreach(KeyValuePair<Key, Value> entry in Dict)
            {
                stb.Append(entry.Key);
                stb.Append('=').Append('"');
                stb.Append(entry.Value);
                stb.Append('"');
            }
            return stb.ToString();
        }

    }
}
