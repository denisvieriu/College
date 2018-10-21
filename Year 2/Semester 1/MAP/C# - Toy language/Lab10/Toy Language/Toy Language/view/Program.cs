using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Toy_Language.controller;
using Toy_Language.repository;
using Toy_Language.view;

namespace Toy_Language
{
    class Program
    {
        static void Main(string[] args)
        {
            IRepository repo = new Repository();
            Controller ctrl = new Controller(repo);
            View view = new View(ctrl);

            view.ExecuteMainMenu();
        }
    }
}
