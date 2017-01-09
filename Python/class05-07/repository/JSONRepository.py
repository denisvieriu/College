'''
Created on Jan 8, 2017

@author: Denis
'''

from repository.Repository import Repository
import json

class JSONRepository(Repository):
    def __init__(self, fileName="newFile.txt", objectType="None"):
        Repository.__init__(self)
        self._fileName = fileName
        
        
    def _readFromFile(self):
        with open(self._fileName, "r") as f:
            s = f.read()
            self._entities = json.loads(s)
        
        