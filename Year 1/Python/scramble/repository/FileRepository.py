'''
Created on Jan 26, 2017

@author: Denis
'''

class FileRepository():
    '''
    The repository where we'll load all the data from the file into
    an list
    '''
    def __init__(self, filename):
        
        self._objects = []
        self._filename = filename
        self._readFromFile()
        
    def _readFromFile(self):
        with open(self._filename) as f:
            lines = f.readlines()
            for line in lines:
                if line != '\n':
                    self._objects.append(line)
                    