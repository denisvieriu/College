'''
Created on Dec 13, 2016

@author: Denis
'''
from controller.StudentController import StudentController
from controller.UndoController import FunctionCall, Operation

class StudentControllerWithUndo(StudentController):
    def __init__(self, undoController, studentRepo, studentValidator, gradeController):
        StudentController.__init__(self, studentRepo, studentValidator)
        self._undoController = undoController
        self._gradeController = gradeController
        
    def addStudent(self, ident, name):
        s = StudentController.addStudent(self, ident, name)
        '''
        If the operation did not raise an Exception, then we record it for Undo/Redo
        '''
        redo = FunctionCall(self.addStudent, ident, name)
        undo = FunctionCall(self.removeStudent, ident)
        operation = Operation(redo, undo)
        self._undoController.recordOperation(operation)
        return s

    def removeStudent(self, ident):
        '''
        Removes a student
        '''
        name = self._studentRepo[ident].name
        StudentController.removeStudent(self, ident)
        redo = FunctionCall(self.removeStudent, ident)
        undo = FunctionCall(self.addStudent, ident, name)
        operation = Operation(redo, undo)
        self._undoController.recordOperation(operation)
        self._gradeController.removeStudent(ident)
        
        
        
    def updateStudent(self, ident, newName):
        oldName = self._studentRepo[ident].name
        StudentController.updateStudent(self, ident, newName)
        redo = FunctionCall(self.updateStudent, ident, newName)
        undo = FunctionCall(self.updateStudent, ident, oldName)
        operation = Operation(redo, undo)
        self._undoController.recordOperation(operation)
        
        

