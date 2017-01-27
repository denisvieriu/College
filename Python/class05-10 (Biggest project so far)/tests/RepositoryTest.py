'''
Created on Nov 25, 2016

@author: Denis
'''

import unittest
from repository.Repository import Repository
from repository.RepositoryException import RepositoryException
from domain.Student import Student

class RepositoryTestCase(unittest.TestCase):
    def setUp(self):
        self._repo = Repository()
        
    def testRepo(self):
        '''
        Testing store
        '''
        self.assertEqual(len(self._repo), 0)
        s = Student("1", "Nei")
        self._repo.store(s)
        self.assertEqual(len(self._repo), 1)
        self.assertRaises(RepositoryException , self._repo.store, s)
        s = Student("2", "Ker")
        self._repo.store(s)
        self.assertEqual(len(self._repo), 2)
        
        '''
        Testing remove from repo
        '''
        self._repo.remove("1")
        self.assertEqual(len(self._repo), 1)
        self.assertRaises(RepositoryException, self._repo.remove, "1")
        
        
        
