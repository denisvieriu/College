using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Toy_Language.utils.adt
{
    public interface IFileTable<K, V>
    {
        void Add(K key, V val);
        void Remove(K key);
        V Get(K key);
        bool Contains(K key);
        IDictionary<K, V> GetAll();
    
    }
}
