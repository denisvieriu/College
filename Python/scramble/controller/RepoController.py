'''
Created on Jan 26, 2017

@author: Denis
'''

import random
from copy import deepcopy
class RepoController:
    def __init__(self, repository):
        self._repo = repository
       
        
    def startGame(self):
        randomSeq = self._repo._objects[random.randint(0, len(self._repo._objects) - 1)]
        listRandomSeq = randomSeq.split()
        listRandomSeq = list(map(lambda x : list(x), listRandomSeq))
        points = sum(c.isalpha() for c in randomSeq) 
        for i in range(0, points):
            try:
                firstWord = listRandomSeq[random.randint(0, len(listRandomSeq) - 1)]
                secondWord = listRandomSeq[random.randint(0, len(listRandomSeq) - 1)]
                firstLetter = random.randint(1, len(firstWord) - 2)
                secondLetter = random.randint(1, len(secondWord) - 2)
                firstWord[firstLetter], secondWord[secondLetter] = secondWord[secondLetter], firstWord[firstLetter]
            except:
                pass
    
        return points, randomSeq, listRandomSeq
    
    def shuffle(self, l, firstWord, firstLetter, secondWord, secondLetter):
        l[firstWord][firstLetter], l[secondWord][secondLetter] = l[secondWord][secondLetter], l[firstWord][firstLetter]
        
                
