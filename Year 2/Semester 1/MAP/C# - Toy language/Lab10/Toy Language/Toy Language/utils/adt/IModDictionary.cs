using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Toy_Language.utils
{
    public interface IModDictionary<K, V>
    {
        IDictionary<K, V> Dict { get; set; }
        V Get(K key);
        bool Contains(K key);
        void Put(K key, V val);
        void Update(K key, V val);
        IDictionary<K, V> GetAll();
     }
}
