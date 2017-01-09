'''
Created on Nov 19, 2016

@author: Denis
'''

from domain.Grade import Grade
from copy import deepcopy

class GradeController:

    def __init__(self, gradeRepo, gradeValidator, studentRepo, disciplineRepo):
        '''
        Constructor for Grade Controller
        gradeRepo - the grade's repository
        studentRepo - the student's repository
        disciplineRepo - the discipline's repository
        '''
        self._gradeRepo = gradeRepo
        self._gradeValidator = gradeValidator
        self._studentRepo = studentRepo
        self._disciplineRepo = disciplineRepo

    def updateRepositories(self, studentRepo, disciplineRepo):
        '''
        Updates the student repository and discipline repostitory ( to have access all the time of new added students )
        studentRepo - the student's repository
        disciplineRepo - the discipline's repository
        '''
        self._studentRepo = studentRepo
        self._disciplineRepo = disciplineRepo

    def addGrade(self, disciplineId, studentId, grade):
        '''
        Saves a grade to the repository in the format <disciplineId><studentId><gradeValue>
        disciplineId - the disciplineId to save
        studentId - the studentId to save
        grade - the grade value to save
        '''
        g = Grade(disciplineId, studentId, grade)
        self._gradeValidator.validate(g, self._studentRepo, self._disciplineRepo)
        self._gradeRepo.store(g)
    
    def enrollStudent(self, disciplineId, studentId, grade):
        '''
        Enrolls a students at a discipline
        disciplineId - the discipline where the studentId needs to be enrolled
        '''
        g = Grade(disciplineId, studentId, grade)
        self._gradeValidator.validate(g, self._studentRepo, self._disciplineRepo)
        self._gradeRepo.enroll(g)
        

    
    def sortAsc(self): 
        '''
        Statistics : return students alphabetically, key = name 
        '''
        l = self._gradeRepo.getAll()
        l.sort(key=lambda x : self._studentRepo[x.studentId].name.lower())
        return l
    
    def sortAscDisciplines(self): 
        '''
        Helper function for 4th statistic ( sorts grade repository by discipline name ) 
        '''
        l = self._gradeRepo.getAll()
        l.sort(key=lambda x : self._disciplineRepo[x.disciplineId].name.lower())
        return l
    
    
    def sortDescGrades(self):
        '''
        Statistics : returns students in descending order by their average grade 
        '''
        l = self._gradeRepo.getAll()
        l.sort(key=lambda x : GradeController.gradeAverage(x.grade), reverse=True)
        return l
    
    def tryIt(self):
        l = self._gradeRepo.getAll()
        newL = []
        for x in l:
            newL.append(DTO1(x.disciplineId, self._disciplineRepo[x.disciplineId].name, x.studentId))
        newL.sort()
        return newL
    
    
    def failingDisciplines(self):
        '''
        Statistics : returns students who have an average grade < 5 ( meaning they're failing)
        '''
        l = self._gradeRepo.getAll()
        l = filter(lambda x : GradeController.gradeAverage(x.grade) < 5, l)
        return l
         
    def aggregateAverage(self):
        '''
        Statistics: Returns the aggregate average of a student( the average from all disciplines the student is enrolled at)
        '''
        l = deepcopy(self.sortAsc())
        n = len(l)
        for i in range(0, n):
            gradeAvg = GradeController.gradeAverage(l[i].grade) 
            l[i].grade[:] = [gradeAvg]
        i = index = 0
        ident = l[0].studentId
        nrGrades = 1
   
        newL = []
        newL.append(l[0])
        for i in range(1, n):
            if ident == l[i].studentId:
                newL[index].grade[0] += l[i].grade[0]
                l[i] = []
                nrGrades += 1
            else:
                newL[index].grade[0] /= nrGrades
                nrGrades = 1
                index += 1
                ident = l[i].studentId
                newL.append(l[i])
        return newL
    
    def aggregateAverageForDisciplines(self):
        '''
        Statistics : Returns the average at a discipline where there is at least one student enrolled
        '''
        l = deepcopy(self.sortAscDisciplines())
        n = len(l)
        for i in range(0, n):
            gradeAvg = GradeController.gradeAverage(l[i].grade) 
            l[i].grade[:] = [gradeAvg]
        i = index = 0
        ident = l[0].disciplineId
        nrGrades = 1
        
        newL = []
        newL.append(l[0])
        for i in range(1, n):
            if ident == l[i].disciplineId:
                newL[index].grade[0] += l[i].grade[0]
                l[i] = []
                nrGrades += 1
            else:
                newL[index].grade[0] /= nrGrades
                nrGrades = 1
                index += 1
                ident = l[i].studentId
                newL.append(l[i])
                
        newL.sort(key=lambda x : GradeController.gradeAverage(x.grade), reverse=True)
        return newL
        
        
    
    def listToString(self, l):
        '''
        Converts a list into string format
        '''
        s = ""
        for grade in l:
            if grade != []:
                s += "Discipline id: " + str(grade.disciplineId) + " Discipline name: " + str(self._disciplineRepo[grade.disciplineId].name) + "| Student Id: " + str(grade.studentId) + " Student name: " + str(self._studentRepo[grade.studentId].name) + "| Grade average: " + str(GradeController.gradeAverage(grade.grade)) + "\n"
        return s
    
    def listToString2(self, l):
        '''
        Converts a list into string format
        '''
        s = ""
        for grade in l:
            if grade != []:
                s += "Student id: " + str(grade.studentId) + ", Student name: " + str(self._studentRepo[grade.studentId].name) + ", has an average grade of " + str(grade.grade[0]) + "\n"
        return s
    
    def listToString3(self, l):
        '''
        Used for 4th statistics
        '''
        s = ""
        for grade in l:
            if grade != []:
                s += "Disicipline id: " + str(grade.disciplineId) + ", Discipline name: " + str(self._disciplineRepo[grade.disciplineId].name) + ", has average grade within all students enrolled at it: " + str(grade.grade[0]) + "\n"
        return s
           
  
    @staticmethod    
    def gradeAverage(grades):
        '''
        Average grade withing a discipline
        Output : s - the grade average
        '''
        s = 0
        it = 0
        for i in grades:
            s = s + i
            it += 1
        s = s / it
        return s
    
    
    def removeStudent(self, ident):
        '''
        Removes a student found by ident(id) from grade repository
        '''
        # self._gradeRepo.removeStudent(ident)
        i = 0
        while i < len(self._gradeRepo._objects):
            if self._gradeRepo._objects[i]._studentId == ident:
                del self._gradeRepo._objects[i]
                i -= 1
            i += 1
        
    def removeDiscipline(self, ident):
        '''
        Removes a discipline found by ident(id) from grade repository
        '''
        self._gradeRepo.removeDiscipline(ident)

    def getGrades(self):
        '''
        Returns grade repository
        '''
        return self._gradeRepo.getAll()

    def __str__(self):
        return str(self._gradeRepo)
    
    def __len__(self):
        return len(self._gradeRepo.getAll())
    
    
class DTO1:
    def __init__(self, disciplineId, disciplineName, studentId):
        self._disciplineId = disciplineId
        self._disciplineName = disciplineName
        self._studentId = studentId

    def get_discipline_id(self):
        return self.__disciplineId


    def get_student_id(self):
        return self.__studentId

    disciplineId = property(get_discipline_id, None, None, "disciplineId's docstring")
    studentId = property(get_student_id, None, None, "studentId's docstring")
    
    def __lt__(self, other):
        return self._studentId < self._disciplineId

    def __str__(self):
        return str(self._disciplineId) + " wow " + str(self._disciplineName) + " wowww" 
   

        
    
        
