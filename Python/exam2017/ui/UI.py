'''
Created on Jan 27, 2017

@author: Denis
'''
from repository.FileRepository import RepositoryException
from controller.RepoController import ControllerException
class UI:
    def __init__(self, controller):
        self._controller = controller
    
    @staticmethod
    def menu():
        s = "Avalaible commands:\n"
        s += "\tadd <id>;<text>;<choice_a>;<choice_b>;<choice_c>;<correct_choice>;<difficulty>\n"
        s += "\tcreate <difficulty> <number_of_questions> <file>\n"
        s += "\tstart <file>\n"
        
        return s
    
    @staticmethod
    def addToMaster(controller, l):
        controller.addQuestion(l)
        
    @staticmethod
    def createQuiz(controller, l):
        controller.createQuiz(l)
        
    @staticmethod
    def startQuiz(controller, l):
        return controller.startQuiz(l)
        
    @staticmethod
    def printQuiz(controller, l):
        points = 0
        for x in l:
            print(controller.toString(x))
            command = input ("Your answer:")
            points += controller.correctAnswer(x, command)
        print("Well done! Your score " + str(points) + ".")
        
    def mainMenu(self):
        print(UI.menu())
        while(True):
            command = input()
            commandToList = command.split(maxsplit=1)
            try:
                if commandToList[0] == "add":
                    UI.addToMaster(self._controller, commandToList[1])
                elif commandToList[0] == "create":
                    UI.createQuiz(self._controller, commandToList[1])
                elif commandToList[0] == "start":
                    l = UI.startQuiz(self._controller, commandToList[1])
                    UI.printQuiz(self._controller, l)
                else:
                    print("invalid command!")
            except RepositoryException as msg:
                print(msg)
            except ControllerException as msg:
                print(msg)
            except:
                pass
          
