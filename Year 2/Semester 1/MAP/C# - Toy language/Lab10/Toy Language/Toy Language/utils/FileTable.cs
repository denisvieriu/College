using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Toy_Language.utils.adt;

namespace Toy_Language.utils
{
    public class FileTable<K, V> : IFileTable<K, V>
    {
        private IDictionary<K, V> dict;

        public FileTable()
        {
            dict = new Dictionary<K, V>();
        }

        public void Add(K key, V value)
        {
            dict.Add(key, value);
        }

        public void Remove(K key)
        {
            dict.Remove(key);
        }

        public V Get(K key)
        {
            V val = default(V);
            dict.TryGetValue(key, out val);
            return val;
        }

        public bool Contains(K key)
        {
            return dict.ContainsKey(key);
        }

        public IDictionary<K, V> GetAll()
        {
            return dict;
        }
    }
}
