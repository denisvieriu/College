'''
Created on Dec 18, 2016

@author: Denis
'''

from repository.Repository import Repository

class FileRepository(Repository):
    def __init__(self, fileName="newFile.txt", objectType="None"):
        Repository.__init__(self)
        self._fileName = fileName
        self._objectType = objectType
        self._readFromLine()
        
    def _readFromLine(self):
        with open(self._fileName, "r") as f:
            lines = f.readlines()
            for line in lines:
                if line != '\n':
                    obj = self._objectType.readFromLine(line)
                    self._entities[obj.getId] = obj
                    
                    
    def _writeToLine(self):
        with open(self._fileName, "w") as f:
            for obj_id in self._entities:
                f.write(self._objectType.writeToLine(self._entities[obj_id]) + '\n')
                        
                        
    def store(self, elem):
        Repository.store(self, elem)
        self._writeToLine()
        
    def remove(self, ident):
        Repository.remove(self, ident)
        self._writeToLine()
        
    def update(self, elem):
        Repository.update(self, elem)
        self._writeToLine()
        
    def __len__(self):
        return len(self._entities)

