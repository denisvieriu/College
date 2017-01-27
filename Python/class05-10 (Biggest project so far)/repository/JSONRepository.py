'''
Created on Jan 8, 2017

@author: Denis
'''

from repository.Repository import Repository
import json


class JSONRepository(Repository):
    def __init__(self, fileName="newFile.txt"):
        Repository.__init__(self)
        self._fileName = fileName
        
        
    def _readFromFile(self):
        with open(self._fileName, "r") as f:
            s = f.read()
            self._entities = json.loads(s)
            
    def _writeToLine(self):
        with open(self._fileName, "w") as f:
            f.write(json.dumps(self._entities))        
            
    def store(self, elem):
        Repository.store(self, elem)
        self._writeToLine()
        
    def remove(self, ident):
        Repository.remove(self, ident)
        self._writeToLine()
        
    def update(self, elem):
        Repository.update(self, elem)
        self._writeToLine()
            
            
            
        
