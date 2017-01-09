'''
Created on Dec 13, 2016

@author: Denis
'''

from controller.GradeController import GradeController
from controller.UndoController import FunctionCall, Operation

class GradeControllerWithUndo(GradeController):

    def __init__(self, undoController, gradeRepo, gradeValidator, studentRepo, disciplineRepo):
        GradeController.__init__(self, gradeRepo, gradeValidator, studentRepo, disciplineRepo)
        self._undoController = undoController
      
    
    def newRemove(self, disciplineId, studentId):
        for i in range(0, len(self._gradeRepo._objects)):
            if self._gradeRepo._objects[i]._studentId == studentId and self._gradeRepo._objects[i]._disciplineId == disciplineId:
                del self._gradeRepo._objects[i]
                break
    
    def removeLastGrade(self, disciplineId, studentId):
        for i in range(0, len(self._gradeRepo._objects)):
            if self._gradeRepo._objects[i]._studentId == studentId and self._gradeRepo._objects[i]._disciplineId == disciplineId:
                del self._gradeRepo._objects[i].grade[-1]
                break
            
            
    def addGrade(self, disciplineId, studentId, grade):
        GradeController.addGrade(self, disciplineId, studentId, grade)
        '''
        '''
        redo = FunctionCall(self.addGrade, disciplineId, studentId, grade)
        undo = FunctionCall(self.removeLastGrade, disciplineId, studentId)
        operation = Operation(redo, undo)
        self._undoController.recordOperation(operation)
            
    def enrollStudent(self, disciplineId, studentId, grade):
        GradeController.enrollStudent(self, disciplineId, studentId, grade)
        '''
        '''
        redo = FunctionCall(self.enrollStudent, disciplineId, studentId, grade)
        undo = FunctionCall(self.newRemove, disciplineId, studentId)
        operation = Operation(redo, undo)
        self._undoController.recordOperation(operation)
        
    def add(self, l):
        self._gradeRepo._objects.append(l)
        
    def doNothing(self):
        pass
    
    def removeStudent(self, ident):
        i = 0
        ranOneTime = False
        while i < len(self._gradeRepo._objects):
            if self._gradeRepo._objects[i]._studentId == ident:
                ranOneTime = True
                l = self._gradeRepo._objects[i]
                del self._gradeRepo._objects[i]
                undo = FunctionCall(self.add, l)
                redo = FunctionCall(self.doNothing)
                operation = Operation(redo, undo)
                self._undoController.recordOperation(operation)
                i -= 1
            i += 1
            
    
        
        if ranOneTime == True:
            undo = FunctionCall(self.doNothing)
            redo = FunctionCall(self.removeStudent, ident)
            operation = Operation(redo, undo)
            self._undoController.recordOperation(operation)
            
            
    def removeDiscipline(self, ident):
        i = 0
        ranOneTime = False
        while i < len(self._gradeRepo._objects):
            if self._gradeRepo._objects[i].disciplineId == ident:
                ranOneTime = True
                l = self._gradeRepo._objects[i]
                del self._gradeRepo._objects[i]
                undo = FunctionCall(self.add, l)
                redo = FunctionCall(self.doNothing)
                operation = Operation(redo, undo)
                self._undoController.recordOperation(operation)
                i -= 1
            i += 1
            
    
        
        if ranOneTime == True:
            undo = FunctionCall(self.doNothing)
            redo = FunctionCall(self.removeDiscipline, ident)
            operation = Operation(redo, undo)
            self._undoController.recordOperation(operation)
        
