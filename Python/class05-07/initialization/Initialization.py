'''
Created on Nov 19, 2016

@author: Denis
'''

from controller.StudentControllerWithUndo import StudentControllerWithUndo
from controller.DisciplineControllerWithUndo import DisciplineControllerWithUndo
from controller.GradeControllerWithUndo import GradeControllerWithUndo
from controller.UndoController import UndoController
from repository.Repository import Repository, GradeRepository
from domain.Student import StudentValidator
from domain.Discipline import DisciplineValidator
from domain.Grade import GradeValidator


from utils.PersistencyHandler import PersistencyHandler

def initAll():
    
    studentRepository, disciplineRep, gradeRepo = PersistencyHandler.obtainPersistencySource()
    
    
    undoController = UndoController()
    undoController._recorded = False
    
    
    studentValidator = StudentValidator()
    
    
    disciplineVal = DisciplineValidator()
    
    
    gradeValid = GradeValidator()
    

    
    gradeController = GradeControllerWithUndo(undoController, gradeRepo, gradeValid, studentRepository, disciplineRep)
    studentController = StudentControllerWithUndo(undoController, studentRepository, studentValidator, gradeController)
    disciplineController = DisciplineControllerWithUndo(undoController, disciplineRep, disciplineVal, gradeController)
    
    studentController.addStudent("1", "Ami")
    studentController.addStudent("80", "Bobo")
    studentController.addStudent("9", "Gicu")
    studentController.addStudent("121", "Marcel")
    studentController.addStudent("abc", "Dan")
    studentController.addStudent("7805", "Mercury")
    studentController.addStudent("a12", "Fred")
    studentController.addStudent("8", "Dennis")
    studentController.addStudent("9011", "Smith")
    studentController.addStudent("bx88", "Peter")
    
    
    # len = 10
    


   
   
    disciplineController.addDiscipline("1", "Humanities")
    disciplineController.addDiscipline("5", "Geography")
    disciplineController.addDiscipline("9", "Religion")
    disciplineController.addDiscipline("ab22", "Archaeology")
    disciplineController.addDiscipline("3030", "Economics")
    disciplineController.addDiscipline("1010", "Medicine")
    disciplineController.addDiscipline("1bcs", "Philosophy")
    disciplineController.addDiscipline("964", "Arts")
    disciplineController.addDiscipline("135", "Literature")
    disciplineController.addDiscipline("aabb", "Mathematics")
    disciplineController.addDiscipline("19ac1", "Logic")
    disciplineController.addDiscipline("9347", "Statistics")
    disciplineController.addDiscipline("10046", "Agriculture")
   
    # len = 13



    


   
    gradeController.enrollStudent("10046", "9011", [0])
    gradeController.addGrade("10046", "9011", [2])
    gradeController.addGrade("10046", "9011", [8])
    gradeController.addGrade("10046", "9011", [10])
    gradeController.enrollStudent("1", "1", [0])
    gradeController.addGrade("1", "1", [6])
    gradeController.addGrade("1", "1", [8])
    gradeController.addGrade("1", "1", [2])
    gradeController.addGrade("1", "1", [10])
    gradeController.enrollStudent("5", "80", [0])
    gradeController.addGrade("5", "80", [10])
    gradeController.addGrade("5", "80", [9])
    gradeController.enrollStudent("9347", "1", [0])
    gradeController.addGrade("9347", "1", [4])
    gradeController.addGrade("9347", "1", [3])
    gradeController.enrollStudent("19ac1", "1", [0])
    gradeController.addGrade("19ac1", "1", [3])
    gradeController.addGrade("19ac1", "1", [3])
    gradeController.addGrade("19ac1", "1", [3])
   
    gradeController.enrollStudent("1", "bx88", [0])
   
    gradeController.addGrade("1", "bx88", [10])
   
    # len = 5
    
    
    undoController._recorded = True
    return undoController, studentController, disciplineController, gradeController

    

