'''
Created on Dec 30, 2016

@author: Denis
'''

from repository.Repository import GradeRepository
from copy import deepcopy

class FileGradeRepository(GradeRepository):
    def __init__(self, fileName="newFile.txt", objectType="None"):
        GradeRepository.__init__(self)
        self._fileName = fileName
        self._objectType = objectType
        self._readFromLine()
        
    def _readFromLine(self):
        with open(self._fileName, "r") as f:
            lines = f.readlines()
            for line in lines:
                if line != '\n':
                    obj = self._objectType.readFromLine(line)
                    copyobj = deepcopy(obj)
                    copyobj.grade[0] = 0
                    GradeRepository.enroll(self, copyobj)
                    GradeRepository.store(self, obj)
                    
                    
    def _writeToLine(self):
        with open(self._fileName, "w") as f:
            for obj in self._objects:
                f.write(self._objectType.writeToLine(obj))
                        
                        
    def store(self, elem):
        GradeRepository.store(self, elem)
        self._writeToLine()
        
    def removeStudent(self, ident):
        GradeRepository.removeStudent(self, ident)
        self._writeToLine()
        
    def removeDiscipline(self, ident):
        GradeRepository.removeDiscipline(self, ident)
        self._writeToLine()
    

