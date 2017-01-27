class UndoController:
    def __init__(self):
        self._operations = []
        self._index = -1    
        self._recorded = True

    def recordOperation(self, operation):
        if self.isRecorded() == True:
            self._operations[-1].append(operation)

    def newOperation(self):
        if self.isRecorded() == False:
            return

        self._operations = self._operations[0:self._index + 1]
        self._operations.append([])
        self._index += 1

    def isRecorded(self):
        '''
        NB!
        
        If you can't figure out what this is good for, try removing it :)
        '''
        return self._recorded

    def undo(self):
        if self._index < 0:
            return False
        
        self._recorded = False

        for oper in self._operations[self._index]:
            oper.undo()
    
        self._recorded = True
    
        self._index -= 1
        return True
    
    def redo(self):
        if self._index == len(self._operations) - 1:
            return False
        
        self._recorded = False
        
        for oper in self._operations[self._index + 1]:
            oper.redo()

            
        self._recorded = True
        
        self._index += 1
        return True
        

class FunctionCall:
    def __init__(self, functionRef, *parameters):
        self._functionRef = functionRef
        self._parameters = parameters

    def call(self):
        self._functionRef(*self._parameters)

class Operation:
    def __init__(self, functionDo, functionUndo):
        self._functionDo = functionDo
        self._functionUndo = functionUndo

    def undo(self):
        self._functionUndo.call()

    def redo(self):
        self._functionDo.call()
