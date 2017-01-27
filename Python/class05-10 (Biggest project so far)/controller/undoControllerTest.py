'''
Created on Dec 13, 2016

@author: Denis
'''

from controller.UndoController import UndoController
from repository.Repository import Repository, GradeRepository
from controller.StudentControllerWithUndo import StudentControllerWithUndo
from controller.GradeControllerWithUndo import GradeControllerWithUndo
from controller.DisciplineControllerWithUndo import DisciplineControllerWithUndo
from domain.Student import StudentValidator
from domain.Grade import GradeValidator
from domain.Discipline import DisciplineValidator

msg = "_" * 30

def undoTest():
    '''
    '''
    undoController = UndoController()
    studentRepo = Repository()
    disciplineRepo = Repository()
    
    
    '''
    Start grade Controller
    '''
    gradeRepo = GradeRepository()
    gradeValidator = GradeValidator()
    gradeController = GradeControllerWithUndo(undoController, gradeRepo, gradeValidator, studentRepo, disciplineRepo)
    
    '''
    Start student Controller
    '''
    studentValidator = StudentValidator()
    studentController = StudentControllerWithUndo(undoController, studentRepo, studentValidator, gradeController)
    
    '''
    Start discipline Controller
    '''
    disciplineValidator = DisciplineValidator()
    disciplineController = DisciplineControllerWithUndo(undoController, disciplineRepo, disciplineValidator, gradeController)
    #######################################################
    
    
    undoController.newOperation()
    studentA = studentController.addStudent("cc11", "Ion")

    undoController.newOperation()
    studentB = studentController.addStudent("ab133", "Ana")
    
    undoController.newOperation()
    studentC = studentController.addStudent("1", "George")
    
    undoController.newOperation()
    studentD = studentController.addStudent("2", "Mihai")
    
    
    '''
    print("Len of Student Controller: ", len(studentController))
    print(msg)
    print(studentController)
    undoController.undo()
    undoController.undo()
    undoController.undo()
    undoController.undo()
    print(msg)
    
    print("Len of Student Controller: ", len(studentController))
    print(msg)   
    
    undoController.redo()
    undoController.redo()
    undoController.redo()
    undoController.redo()
     
    print("Len of Student Controller: ", len(studentController))
    print(msg)   
    print(studentController)
    '''
    
    undoController.newOperation()
    disciplineA = disciplineController.addDiscipline("1", "Math")
    
    undoController.newOperation()
    disciplineB = disciplineController.addDiscipline("2", "Physics")
    
    undoController.newOperation()
    disciplineC = disciplineController.addDiscipline("3", "Grammar")
    
    undoController.newOperation()
    disciplineD = disciplineController.addDiscipline("4", "C++")
    
    '''
    print("Len of DisciplineController Controller: ", len(disciplineController))
    print(msg)
    print(disciplineController)
    undoController.undo()
    undoController.undo()
    undoController.undo()
    undoController.undo()
    print(msg)
    
    print("Len of DisciplineController Controller: ", len(disciplineController))
    print(msg)
    print(disciplineController)
    
    undoController.redo()
    undoController.redo()
    undoController.redo()
    undoController.redo()
    print(msg)
    print("Len of DisciplineController Controller: ", len(disciplineController))
    print(disciplineController)
    '''
    
    gradeController.enrollStudent("1", "cc11", [0])
    gradeController.addGrade("1", "cc11", [7])
    gradeController.addGrade("1", "cc11", [10])
    
    gradeController.enrollStudent("2", "cc11", [0])
    gradeController.addGrade("2", "cc11", [9])
    gradeController.addGrade("2", "cc11", [2])
    
    gradeController.enrollStudent("1", "ab133", [0])
    gradeController.addGrade("1", "ab133", [10])
    gradeController.addGrade("1", "ab133", [3])
    
    gradeController.enrollStudent("1", "1", [0])
    gradeController.addGrade("1", "1", [10])
    gradeController.addGrade("1", "1", [3])
    
    print(msg)
    print("Len of GradeController: ", len(gradeController))
    print(gradeController)
    print(msg)
    
    undoController.newOperation()
    studentController.removeStudent("1")
    
    undoController.newOperation()
    studentController.removeStudent("ab133")
    
    print(msg)
    print(studentController)
    print(msg)
    print(msg)
    
    print("Len of GradeController: ", len(gradeController))
    print(gradeController)
    
    undoController.undo()
    print(gradeController)
    
    
    undoController.undo()
    print(gradeController)
    
    
    
    
    
undoTest()
