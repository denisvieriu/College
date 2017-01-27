'''
Created on Jan 6, 2017

@author: Denis
'''


import unittest
from domain.Student import Student
from repository.PickleRepository import PickleRepository
class TestFileRepoStudents(unittest.TestCase):

    def setUp(self):
        self._student = Student("1", "Alex")
        self._repo = PickleRepository("students.pickle")
        
    def tearDown(self):
        del self._student
        del self._repo
    
    def testName(self):
        self._repo.store(self._student)
        self.assertEqual(len(self._repo), 1)
        s = Student("1", "Gigi")
        self._repo.update(s)
        self.assertEqual(self._repo.getById("1").getId, "1")
        self._repo.remove("1")
        self.assertEqual(len(self._repo), 0)
        
        
if __name__ == "__main__":
    # import sys;sys.argv = ['', 'Test.testName']
    unittest.main()
