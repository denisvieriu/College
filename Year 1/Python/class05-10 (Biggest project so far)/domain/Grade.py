'''
Created on Nov 19, 2016

@author: Denis
'''

from domain.ValidatorException import ValidatorException


class Grade:
    '''
    classdocs
    '''
    @staticmethod
    def readFromLine(line):
        words = line.split(',')
        return Grade(words[0], words[1], [int(words[2])])
    
    @staticmethod
    def writeToLine(obj):
        s = ""
        for currentGrade in obj.grade:
            s += str(obj.disciplineId) + ',' + str(obj.studentId) + ',' + str(currentGrade) + '\n'
        return s
    
    def __init__(self, disciplineId, studentId, grade):
        '''
        Constructor
        '''
        self._disciplineId = disciplineId
        self._studentId = studentId
        self._grade = grade

    @property
    def disciplineId(self):
        return self._disciplineId

    @property
    def studentId(self):
        return self._studentId

    @property
    def grade(self):
        return self._grade

    @grade.setter
    def grade(self, value):
        self._grade = value

    def __str__(self):
        return "Disciple's id: " + str(self.disciplineId) + ", Student's id: " + str(self.studentId) + ", Grade value: " + str(self.grade)
    


class GradeValidator:

    @staticmethod
    def validate(g, studentRepo, disciplineRepo):
        _errors = ""
        try:
            g.grade[0] = int(g.grade[0])
        except ValueError:
            raise ValidatorException("Grade must be an integer!")
        if g.studentId not in studentRepo._entities:
            _errors = "Student's id not found!\n"
        if g.disciplineId not in disciplineRepo._entities:
            _errors += "Discipline's id not found\n"
        if (g.grade[0] < 1 or g.grade[0] > 10) and g.grade[0] != 0:
            _errors += "Grade can't be smaller than 1/ higher than 10!\n"

        if len(_errors) > 0:
            raise ValidatorException(_errors)
