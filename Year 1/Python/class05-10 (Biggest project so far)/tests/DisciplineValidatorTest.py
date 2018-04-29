'''
Created on Nov 25, 2016

@author: Denis
'''

import unittest
from domain.Discipline import Discipline, DisciplineValidator
from domain.ValidatorException import ValidatorException
from controller.DisciplineController import DisciplineController
from repository.Repository import Repository
from repository.RepositoryException import RepositoryException
 
class DisciplineTestCase(unittest.TestCase):
    def setUp(self):
        self.v = DisciplineValidator()
        self.d = Discipline("1", "Math")
        self.r = Repository()
        self.c = DisciplineController(self.r, self.v)
        
    '''
    testing the discipline class(not controller)
    '''
    def testDisciplineValidator(self):
        self.assertTrue(self.v.validate, self.d)
        self.d.name = ""
        self.assertRaises(ValidatorException, self.v.validate, self.d)
        self.d.name = None
        self.assertRaises(ValidatorException, self.v.validate, self.d)
        self.d.name = "dig1ts"
        self.assertRaises(ValidatorException, self.v.validate, self.d)
        
    '''
    Discipline controller test case
    '''
    
    def test(self):
        '''
        Test add discipline
        '''
        self.c.addDiscipline("1", "Ana")
        self.assertEqual(len(self.c), 1)
        self.assertRaises(RepositoryException, self.c.addDiscipline, "1", "Again")
        self.assertEqual(len(self.c), 1)   
        self.assertRaises(ValidatorException, self.c.addDiscipline, "3", "A71nc")
        self.c.addDiscipline("2", "Ion")
        self.assertRaises(ValidatorException, self.c.addDiscipline, "2", "")
        self.c.addDiscipline("3", "George")
        self.assertEqual(len(self.c), 3)
        
        '''
        Test remove Discipline
        '''
        self.c.removeDiscipline("1")
        self.assertEqual(len(self.c), 2)
        self.assertRaises(RepositoryException, self.c.removeDiscipline, "1")
        self.assertRaises(RepositoryException, self.c.removeDiscipline, "random")
        self.c.removeDiscipline("2")
        self.assertEqual(len(self.c), 1)
        self.assertTrue(self.c.removeDiscipline, "1")
        self.assertEqual(len(self.c), 1)
        
        '''
        Test update Discipline
        '''
        self.c.addDiscipline("2", "Gigi")
        self.assertEqual(len(self.c), 2)
        self.c.updateDiscipline("2", "CoolName")
        self.assertEqual(len(self.c), 2)
        self.assertRaises(RepositoryException, self.c.updateDiscipline, "1", "Ana")
            
 
        