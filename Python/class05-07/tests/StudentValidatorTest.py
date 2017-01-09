'''
Created on Nov 25, 2016

@author: Denis
'''

import unittest
from domain.Student import Student, StudentValidator
from domain.ValidatorException import ValidatorException
from controller.StudentController import StudentController
from repository.Repository import Repository
from repository.RepositoryException import RepositoryException

class StudentTestCase(unittest.TestCase):
    def setUp(self):
        self.v = StudentValidator()
        self.s = Student("1", "Marcel")
        self.r = Repository()
        self.c = StudentController(self.r, self.v)
        
    '''
    testing the student class(not controller)
    '''
    def testStudentValidator(self):
        self.assertTrue(self.v.validate, self.s)
        self.s.name = ""
        self.assertRaises(ValidatorException, self.v.validate, self.s)
        self.s.name = None
        self.assertRaises(ValidatorException, self.v.validate, self.s)
        self.s.name = "an12a"
        self.assertRaises(ValidatorException, self.v.validate, self.s)
        
        
    '''
    Student controller test
    '''    

    def test(self):
        '''
        Test add student
        '''
        self.c.addStudent("1", "Ana")
        self.assertEqual(len(self.c), 1)
        self.assertRaises(RepositoryException, self.c.addStudent, "1", "Again")
        self.assertEqual(len(self.c), 1)   
        self.assertRaises(ValidatorException, self.c.addStudent, "3", "A71nc")
        self.c.addStudent("2", "Ion")
        self.assertRaises(ValidatorException, self.c.addStudent, "2", "")
        self.c.addStudent("3", "George")
        self.assertEqual(len(self.c), 3)
         
        '''
        Test remove student
        '''
        self.c.removeStudent("1")
        self.assertEqual(len(self.c), 2)
        self.assertRaises(RepositoryException, self.c.removeStudent, "1")
        self.assertRaises(RepositoryException, self.c.removeStudent, "random")
        self.c.removeStudent("2")
        self.assertEqual(len(self.c), 1)
        self.assertTrue(self.c.removeStudent, "1")
        self.assertEqual(len(self.c), 1)
        
        '''
        Test update student
        '''
        self.c.addStudent("2", "Gigi")
        self.assertEqual(len(self.c), 2)
        self.c.updateStudent("2", "CoolName")
        self.assertEqual(len(self.c), 2)
        self.assertRaises(RepositoryException, self.c.updateStudent, "1", "Ana")
            
    
            
       
            

       
      

        
        
        
        
        
