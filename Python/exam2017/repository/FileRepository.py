'''
Created on Jan 27, 2017

@author: Denis
'''

class RepositoryException(Exception):
    pass

class FileRepository:

    def __init__(self, fileName):
        '''
        Initialising the file repository
        Input : fileName - the file where the master question list will be saved
        Output -
        '''
        self._entities = {}
        self._fileName = fileName
        self._readFromFile()
        
    
    def store(self, ident, text, choice_a, choice_b, choice_c, correct_choice, difficulty):
        '''
        Stores a question to our dictionary ( self._entities )
        Input : ident - the id of our question
                text - the question
                choice_a - first possible answer
                choice_b - second possible answer
                choice_c - third possible answer
                correct_choice
                difficulty - the difficulty of the problem
        Output: -
        raises RepositoryException if the id is already in the repo
        '''
        
        if ident in self._entities:
            raise RepositoryException("Id already in repo!")
        l = [ident, text, choice_a, choice_b, choice_c, correct_choice, difficulty]
        self._entities[ident] = l
        self._writeToFile()
            
    def _readFromFile(self):
        '''
        Read all the data from the file and stores it in our in-memory repository
        Input : - 
        Output : -
        '''
        with open(self._fileName, "r") as f:
            lines = f.readlines()
            for line in lines:
                if line != '\n':
                    l = line.strip().split(";")
                    self.store(l[0], l[1], l[2], l[3], l[4], l[5], l[6])
                    
    def remove(self, ident):
        '''
        Removes an element from our dictionary
        Input - id to be removed
        Output - 
        '''
        del self._entities[ident]
        self._writeToFile()
                    
    def _writeToFile(self):
        '''
        Write our in-memory repository in master question list file
        Input - 
        Output - 
        '''
        with open(self._fileName, "w") as f:
            for key in self._entities:
                f.write(FileRepository.makeString(self._entities[key]) + '\n')
                
    
    def filterDifficulty(self, param):
        '''
        Filters the repository in circumstance of difficulty
        Input - param : the difficulty to be filter after , eg : easy, medium, hard
        Output - l : the new list filtred
        '''
        l = []
        for x in self._entities.values():
            if x[6] == param:
                l.append(x)
        return l
    
    
        
                
    @staticmethod
    def makeString(entity):
        '''
        Convers a list intro a string with a specific format to be written in file
        Input - entity : the list to be converted into string
        Output - s : representing our string
        '''
        s = ""
        for x in entity:
            s = s + str(x) + ";"
        return s
            
                        
    def __len__(self):
        return len(self._entities)
        
    
        
        
        
