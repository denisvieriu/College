'''
Created on Nov 19, 2016

@author: Denis
'''

from domain.IDObject import IDObject
from domain.ValidatorException import ValidatorException


class Student(IDObject):
    
    @staticmethod
    def readFromLine(line):
        words = line.split(',')
        return Student(words[0], words[1])
    
    @staticmethod
    def writeToLine(obj):
        return str(obj.getId) + "," + obj.name.strip()

    def __init__(self, studentId, name):
        '''
        Constructor for student class
        '''
        IDObject.__init__(self, studentId)
        self._name = name

    @property
    def name(self):
        return self._name

    @name.setter
    def name(self, value):
        self._name = value


    def __str__(self):
        return "Id: " + str(self._objectId) + ", Name: " + self._name 
    
    

class StudentValidator():

    def validate(self, student):
        '''
        Validates a student
        '''
        _errors = ""
        if student.name == None or any(char.isdigit() for char in student.name) == True or student.name == "":
            _errors = "Student's name can't be empty or contain digits!"
        if len(_errors) > 0:
            raise ValidatorException(_errors)


def testStudent():
    s1 = Student("76", "George")
    s2 = Student("15", "Ana")
    assert s1.name == "George"
    assert s1.getId == "76"
    assert s2.name == "Ana"
    assert s2.getId == "15"

# testStudent()
