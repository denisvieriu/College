'''
Created on Jan 27, 2017

@author: Denis
'''
import unittest
from controller.RepoController import RepoController, ControllerException
from repository.FileRepository import FileRepository

class Test(unittest.TestCase):


    def setUp(self):
        self._controller = RepoController(FileRepository("masterController.txt"))
        


    def tearDown(self):
        del self._controller


    def testName(self):
        self.assertEqual(len(self._controller), 10)
        self._controller.addQuestion("99;Name El Cid's horse;Babieca;Abu;Santiago;Babieca;hard")
        self.assertEqual(len(self._controller), 11)
        self._controller._repo.remove("99")
        self.assertRaises(ControllerException, self._controller.addQuestion, "1")
        l = self._controller.startQuiz("testQuiz")
        self.assertEqual(len(l), 6)
        self.assertRaises(ControllerException, self._controller.startQuiz, "inexistent")
        self.assertEqual(l[0][2], l[0][5])
        self.assertEqual(l[1][2], l[1][5])
        
if __name__ == "__main__":
    # import sys;sys.argv = ['', 'Test.testName']
    unittest.main()
