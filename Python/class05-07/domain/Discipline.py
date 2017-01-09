'''
Created on Nov 19, 2016

@author: Denis
'''
from domain.IDObject import IDObject
from domain.ValidatorException import ValidatorException


class Discipline(IDObject):
    
    @staticmethod
    def readFromLine(line):
        words = line.split(',')
        return Discipline(words[0], words[1])
    
    @staticmethod
    def writeToLine(obj):
        return str(obj.getId) + ' ' + obj.name

    def __init__(self, disciplineId, name):
        '''
        Constructor
        '''
        IDObject.__init__(self, disciplineId)
        self._name = name

    @property
    def name(self):
        return self._name

    @name.setter
    def name(self, value):
        self._name = value

    def __eq__(self, d):
        if isinstance(d, Discipline) == False:
            return False
        return self._objectId == d._objectId

    def __str__(self):
        return "Id: " + str(self._objectId) + ", Name: " + self._name


class DisciplineValidator:

    @staticmethod
    def validate(d):
        _errors = ""
        if d.name == None or any(char.isdigit() for char in d.name) == True or d.name == "":
            _errors = "Discipline's name can't be empty or contain digits!"
        if len(_errors) > 0:

            raise ValidatorException(_errors)
