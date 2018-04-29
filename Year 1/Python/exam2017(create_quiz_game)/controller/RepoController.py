'''
Created on Jan 27, 2017

@author: Denis
'''


import random

class ControllerException(Exception):
    pass

class RepoController:
    def __init__(self, repo):
        '''
        Constructor
        '''
        self._repo = repo
        
    def addQuestion(self, string):
        '''
        Add a question to our existing master question list
        Input : string - the questiong to be added to our master question list
        Output : 
        raise ControllerException if the command is invalid
        '''
        stringToList = string.split(";")
        if len(stringToList) != 7:
            raise ControllerException("Invalid command!")
        ident = stringToList[0]
        text = stringToList[1]
        choice_a = stringToList[2]
        choice_b = stringToList[3]
        choice_c = stringToList[4]
        correct_choice = stringToList[5]
        difficulty = stringToList[6]
        self._repo.store(ident, text, choice_a, choice_b, choice_c, correct_choice, difficulty)
        
    def startQuiz(self, filename):
        '''
        Starts a quiz, with a given filename ( if the file exists , otherwise raises Controller Exception )
        Input : filename
        Output : a list containg all the data found in the quiz
        raises ControllerException if file doesn't exist
        '''
        try:
            newL = []
            with open(filename, "r") as f:
                lines = f.readlines()
                for line in lines:
                    if line != '\n':
                        l = line.strip().split(";")
                        newL.append([l[0], l[1], l[2], l[3], l[4], l[5], l[6]])
        except:
            raise ControllerException("Inexistent file")
        return newL
    
    @staticmethod
    def correctAnswer(obj, userAnswer):
        '''
        Checks if an answer is correct based on the userAnswer and our list, and returns a number of points
        Input - obj : the obj to be checked
                userAnswer :
        Output - nr of points
        '''
        points = 0
        if obj[5] == userAnswer:
            if obj[6] == "hard":
                points += 3
            elif obj[6] == "medium":
                points += 2
            elif obj[6] == "easy":
                points += 1
        return points
    
    @staticmethod
    def toString(obj):
        return obj[1] + "? " + str(obj[2]) + " | " + str(obj[3]) + " | " + str(obj[4]) + " | "
        
        
            
    def createQuiz(self, l):
        '''
        Creates a quiz randomly, firstly filtering the list about the difficulty and checking if there are enough answers
        Input : l - the parameters
        Output - 
        Writes all the content in a new folder
        raises ControllerException if data is invalid
        '''
        newL = []
        l = l.split()
        if len(l) != 3:
            raise ControllerException("Invalid command!")
        
        difficulty = l[0]
        try:
            number_of_questions = int(l[1])
        except ValueError:
            raise ValueError("Invalid")
        fileName = l[2]
     
        easy = self._repo.filterDifficulty("easy")
        medium = self._repo.filterDifficulty("medium")
        hard = self._repo.filterDifficulty("hard")
        
        
        # our current difficulty
        difficultyFilter = self._repo.filterDifficulty(difficulty)
       
        
        if len(difficultyFilter) < (number_of_questions // 2):
            raise ControllerException("Can't create quiz!")
                 
                 
        i = 0
        while i < (number_of_questions // 2):  # add random questin in the list
            r = random.randint(0, len(difficultyFilter) - 1)
            if difficultyFilter[r] not in newL:
                newL.append(difficultyFilter[r])
                i += 1
                
        l = [easy, medium, hard]
        while i < number_of_questions:  # add the rest of the questions
            firstR = random.randint(0, 2)
            simpleList = l[firstR]
            
            secondR = random.randint(0, len(simpleList) - 1)
            if simpleList[secondR] not in newL:
                newL.append(simpleList[secondR])
                i += 1
            
        newL = RepoController.sortAfterDif(newL)
        
        with open(fileName, "w") as f:
            for obj in newL:
                f.write(RepoController.makeString(obj) + '\n')
            
    
    @staticmethod
    def addToList(l, newL, param):
        '''
        Adds a parameter to list
        Input : newL - new list
                param - parameters
        '''
        for obj in l:
            if obj[6] == param:
                newL.append(obj)
        
    @staticmethod
    def sortAfterDif(l):
        '''
        Sorts a list after difficulty
        Input :  l - our list to be sorted
        Output : the newL sorted
        '''
        newL = []
        RepoController.addToList(l, newL, "easy")
        RepoController.addToList(l, newL, "medium")
        RepoController.addToList(l, newL, "hard")
        return newL
                
        
            
    @staticmethod
    def makeString(entity):
        '''
        Convert an object to string
        Input - entity : the entity to convert into string
        Output - the entity converted into string
        '''
        s = ""
        for x in entity:
            s = s + str(x) + ";"
        return s
            
    def __len__(self):
        return len(self._repo)
        
