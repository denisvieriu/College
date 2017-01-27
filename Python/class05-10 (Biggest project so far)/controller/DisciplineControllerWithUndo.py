'''
Created on Dec 13, 2016

@author: Denis
'''

from controller.DisciplineController import DisciplineController
from controller.UndoController import FunctionCall, Operation

class DisciplineControllerWithUndo(DisciplineController):
    def __init__(self, undoController, disciplineRepo, disciplineValidator, gradeController):
        DisciplineController.__init__(self, disciplineRepo, disciplineValidator)
        self._undoController = undoController
        self._gradeController = gradeController
        
    def addDiscipline(self, ident, name):
        d = DisciplineController.addDiscipline(self, ident, name)
        '''
        '''
        redo = FunctionCall(self.addDiscipline, ident, name)
        undo = FunctionCall(self.removeDiscipline, ident, name)
        operation = Operation(redo, undo)
        self._undoController.recordOperation(operation)
        return d
    
    def removeDiscipline(self, ident):
        name = self._disciplineRepo[ident].name
        DisciplineController.removeDiscipline(self, ident)
        redo = FunctionCall(self.removeDiscipline, ident)
        undo = FunctionCall(self.addDiscipline, ident, name)
        operation = Operation(redo, undo)
        self._undoController.recordOperation(operation)
        self._gradeController.removeDiscipline(ident)
        
    def updateDiscipline(self, ident, newName):
        oldName = self._disciplineRepo[ident].name
        DisciplineController.updateDiscipline(self, ident, newName)
        redo = FunctionCall(self.updateDiscipline, ident, newName)
        undo = FunctionCall(self.updateDiscipline, ident, oldName) 
        operation = Operation(redo, undo)
        self._undoController.recordOperation(operation)   
        
