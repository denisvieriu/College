'''
Created on Jan 11, 2017

@author: Denis
'''
import unittest
from dataStructure.DataStructure import MyStructure


class Test(unittest.TestCase):
    '''
    Testing the new structure created
    '''
    
    def setUp(self):
        self._nr = [8, 5, 3, 1, 9, 6, 0, 7, 4, 2]
        self._data = MyStructure()
    
    def tearDown(self):
        del self._data
        del self._nr
        
    def testName(self):
        
        for nr in self._nr:
            self._data.add(nr)
        
        it = self._data.__iter__()
        for i in range(0, len(self._data)):
            self.assertEqual(self._data[i], it.__next__())
    
        def lt(first, second):
            return first < second
            
        self._data.H_sort(lt)
  
        for i in range(0, 10):
            self.assertEqual(self._data[i], i)
        
        def function(param):
            return param > 5
        
        self.assertEqual(len(self._data.filter(function)), 4)
        

if __name__ == "__main__":
    # import sys;sys.argv = ['', 'Test.testName']
    unittest.main()
