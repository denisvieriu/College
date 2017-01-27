'''
Created on Jan 27, 2017

@author: Denis
'''
import unittest

from repository.FileRepository import FileRepository, RepositoryException
class Test(unittest.TestCase):


    def setUp(self):
        self._repo = FileRepository("masterTest.txt")
        
    def tearDown(self):
        del self._repo

    def testName(self):
        self.assertEqual(len(self._repo), 10)
        self._repo.store("11", "Biggest number", "1", "60", "20", "60", "easy")
        self.assertEqual(len(self._repo), 11)
        self.assertRaises(RepositoryException, self._repo.store, "11", "Biggest number", "1", "60", "20", "60", "easy")
        self._repo.remove("11")
        self.assertEqual(len(self._repo.filterDifficulty("easy")), 3)

if __name__ == "__main__":
    # import sys;sys.argv = ['', 'Test.testName']
    unittest.main()
