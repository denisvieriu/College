'''
Created on Jan 6, 2017

@author: Denis
'''


import pickle
from repository.Repository import GradeRepository, Repository


class PickleRepository(Repository):
    def __init__(self, filename):
        Repository.__init__(self)
        self.__filename = filename
        self.__readFromFile()
        
    def __readFromFile(self):
        with open(self.__filename, "rb") as f:
            try:
                self._entities = pickle.load(f)
            except EOFError:
                self._entities = {}
    
    def __writeToFile(self):
        with open(self.__filename, "wb") as f:
            pickle.dump(self._entities, f)
        
    def store(self, elem):
        Repository.store(self, elem)
        self.__writeToFile()
        
    def remove(self, ident):
        Repository.remove(self, ident)
        self.__writeToFile()
        
    def update(self, elem):
        Repository.update(self, elem)
        self.__writeToFile()
        
        
class PickleGradeRepository(GradeRepository):
    def __init__(self, filename):
        GradeRepository.__init__(self)
        self.__filename = filename
        self.__readFromFile()
        
    def __readFromFile(self):
        with open(self.__filename, "rb") as f:
            try:
                self._objects = pickle.load(f)
            except EOFError:
                self._objects = []
                
    def __writeToFile(self):
        with open(self.__filename, "wb") as f:
            pickle.dump(self._objects, f)
            
            
    def store(self, elem):
        GradeRepository.store(self, elem)
        self.__writeToFile()
        
    def removeStudent(self, ident):
        GradeRepository.removeStudent(self, ident)
        self.__writeToFile()
        
    def removeDiscipline(self, ident):
        GradeRepository.removeDiscipline(self, ident)
        self.__writeToFile()
                
