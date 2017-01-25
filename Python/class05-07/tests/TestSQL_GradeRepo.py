'''
Created on Jan 11, 2017

@author: Denis
'''
import unittest
from domain.Grade import Grade
from repository.SQL_Repo import SQL_GradeRepo

class Test(unittest.TestCase):


    def setUp(self):
        self._grade = Grade("1", "2", [5])
        self._repo = SQL_GradeRepo("grades", Grade)
        
    def tearDown(self):
        del self._grade
        del self._repo
        
    def testName(self):
        """
        Testing all functions that modify the repository
        """
        self._repo.enroll(self._grade)
        self._repo.store(self._grade)
        self.assertEqual(len(self._repo), 1)
        g = Grade("1", "3", [10])
        self._repo.enroll(g)
        self._repo.store(g)
        self.assertEqual(len(self._repo), 2)
        self._repo.removeStudent("2")
        self.assertEqual(len(self._repo), 1)
        self._repo.removeDiscipline("1")
        self.assertEqual(len(self._repo), 0)


if __name__ == "__main__":
    # import sys;sys.argv = ['', 'Test.testName']
    unittest.main()
