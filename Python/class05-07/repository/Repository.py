'''
Created on Nov 19, 2016

@author: Denis
'''

from domain.Student import Student
from domain.Discipline import Discipline
from domain.Grade import Grade
from repository.RepositoryException import RepositoryException
from copy import deepcopy

class Repository:

    def __init__(self):
        self._entities = {}

    def store(self, entity):
        '''
        Saves in the repository an entity
        entity - the entity that will be saved
        Raises RepositoryException if the ID was already stored
        '''
       

        if entity.getId in self._entities:
            raise RepositoryException("Id already in the repo!", entity.getId)
        self._entities[entity.getId] = entity

    def remove(self, ident):
        if ident not in self._entities:
            raise RepositoryException("Id not found in the repo!")
        del self._entities[ident]

    def update(self, entity):
        if entity.getId not in self._entities:
            raise RepositoryException("Id not found in the repo!")
        self._entities[entity.getId] = entity

    def getAll(self):
        l = []
        for e in self._entities.values():
            l.append(e)
        return l

    def getById(self, ident):
        if ident not in self._entities:
            raise RepositoryException("Id not present in the repo!")
        return self._entities[ident]

    def __getitem__(self, index):
        return self.getById(index)

    def __len__(self):
        return len(self._entities)

    def __str__(self):
        first = True
        r = "Empty"
        for val in self._entities.values():
            if first == True:
                if isinstance(val, Student):
                    r = "Student"
                else:
                    r = "Disciplines"
                r += " repository:\n"
                first = False
            r += str(val)
            r += "\n"
        return r


class GradeRepository:

    def __init__(self):
        self._objects = []
    
    def enroll(self, entity):
        i = self._find(entity)
        if i == -1:
            cpy = deepcopy(entity)
            cpy.grade[0] = 0
            self._objects.append(cpy)
        

    def store(self, entity):
        i = self._find(entity)
        entity.grade[0] = int(entity.grade[0])
        if i != -1:
            if self._objects[i].grade[0] == 0:
                self._objects[i].grade[0] = entity.grade[0]
            else:
                self._objects[i].grade.append(entity.grade[0])
        else:
            raise RepositoryException("Student not enrolled!")

    def removeStudent(self, ident):
        i = 0
        while i < len(self._objects):
            if self._objects[i]._studentId == ident:
                del self._objects[i]
                i -= 1
            i += 1
    

    def removeDiscipline(self, ident):
        i = 0
        while i < len(self._objects):
            if self._objects[i]._disciplineId == ident:
                del self._objects[i]
                i -= 1
            i += 1

    def _find(self, entity):

        for i in range(0, len(self._objects)):
            if self._objects[i].disciplineId == entity.disciplineId and self._objects[i].studentId == entity.studentId:
                return i
        return -1

    def getAll(self):
        return self._objects

    def __len__(self):
        return len(self._objects)

    def __str__(self):
        r = "Grade repository:\n"
        for i in self._objects:
            if i.grade[0] != 0:
                r += str(i)
                r += "\n"
        return r
    
    def __getitem__(self, index):
        return self._objects[index]


def testRepository():
    s1 = Student(76, "Ana")
    s2 = Student(726, "Cash")
    d1 = Discipline(12, "Math")
    r1 = Repository()
    r2 = Repository()

    r1.store(s1)
    r1.store(s2)
    print(len(r1))
    r2.store(d1)
    print(r1)
    print(r2)

# testRepository()


def testGradeRepository():
    g = Grade(1, 7, 20)
    gr = GradeRepository()
    gr.enroll()
    gr.store(g)
    print(gr)
