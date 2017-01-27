'''
Created on Nov 19, 2016

@author: Denis
'''

from repository.Repository import Repository
from domain.Discipline import Discipline, DisciplineValidator


class DisciplineController:

    @staticmethod
    def readFromFile(line):
        words = line.split(',')
    def __init__(self, disciplineRepo, disciplineValidator):
        '''
        Discipline class constructor
        disciplineRepo - the discipline's repository
        disciplineValidator - used in validating a new added discipline
        '''
        self._disciplineRepo = disciplineRepo
        self._disciplineValidator = disciplineValidator

    def addDiscipline(self, ident, name):
        '''
        Adds a discipline to the repository
        ident - the id of the discipline
        name - discipline's name
        '''
        d = Discipline(ident, name)
        self._disciplineValidator.validate(d)
        self._disciplineRepo.store(d)
        return d

    def removeDiscipline(self, ident):
        '''
        Removes a discipline based on the id
        ident - the id to be removed
        '''
        self._disciplineRepo.remove(ident)

    def updateDiscipline(self, ident, name):
        '''
        Updates a discipline's name found by id
        ident - the id to search for discipline
        name - the name to be updated
        '''
        entity = self._disciplineRepo[ident]
        if name != "" and name != None:
            entity.name = name
        self._disciplineRepo.update(entity)
        
    def searchForName(self, name):
        '''
        Partial string matching(search for a discipline by her name, the search is case insensitive)
        id - the id to search for ( can't be empty )
        '''
        _entity = []
        d = Discipline(None, name)
        self._disciplineValidator.validate(d)
        for student in self._disciplineRepo.getAll():
            if name.lower() in student.name.lower():
                _entity.append(str(student))
        return _entity
                
    def searchForId(self, ident):
        '''
        Partial string matching(search for a discipline by her id, the search is case insensitive)
        id - the id to search for ( can't be empty )
        '''
        _entity = []
        if ident == None or ident == "":
            return(None)
        for student in self._disciplineRepo.getAll():
            if ident.lower() in student.getId.lower():
                _entity.append(str(student))
        return _entity

    def getDisciplines(self):
        '''
        Returns the discipline repo
        '''
        return self._disciplineRepo.getAll()

    def __str__(self):
        '''
        Overwrite the str operator
        '''
        return str(self._disciplineRepo)
    
    def __len__(self):
        return len(self._disciplineRepo.getAll())


def testDisciplineController():
    disciplineRepository = Repository()
    disciplineValidator = DisciplineValidator()
    dc = DisciplineController(disciplineRepository, disciplineValidator)
    dc.addDiscipline("1", "Math")
    assert len(dc.getDisciplines()) == 1
    try:
        dc.addDiscipline("1", "Spanish")
        assert False
    except:
        assert True




# testDisciplineController()
