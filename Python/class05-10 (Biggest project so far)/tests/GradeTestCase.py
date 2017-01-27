'''
Created on Nov 25, 2016

@author: Denis
'''

import unittest
from initialization.Initialization import *
from domain.ValidatorException import ValidatorException
from domain.Grade import Grade
from repository.RepositoryException import RepositoryException

class GradeTestCAse(unittest.TestCase):
    def setUp(self):
        self.s = initStudents()
        self.d = initDisciplines()
        self.g = initGrades()
        
        
    def test(self):
        self.assertEqual(len(self.g), 6)
    
        self.g.enrollStudent("1", "8", [0])
        self.g.addGrade("1", "8", [10])
        self.assertEqual(len(self.g), 7)
        self.assertRaises(ValidatorException, self.g.addGrade, "1", "random", [7])
        
        
    
        
        
