'''
Created on Nov 19, 2016

@author: Denis
'''

msg1 = "Invalid number of params!"

from repository.RepositoryException import RepositoryException
from domain.ValidatorException import ValidatorException
from copy import deepcopy


class MainMenu():
    
    def __init__(self, studentController, disciplineController, gradeController, undoController):
        self._studentController = studentController
        self._disciplineController = disciplineController
        self._gradeController = gradeController
        self._undoController = undoController
    
    @staticmethod
    def readUserCommand(msg=""):
        return input(msg)
    
    @staticmethod
    def addStudentUI(studentController, dc, gc, uc):
        ident = MainMenu.readUserCommand("Enter the student's ID:")
        name = MainMenu.readUserCommand("Enter the student's name:")
        uc.newOperation()
        studentController.addStudent(ident, name)
        print("Student added successfully!")

    @staticmethod
    def removeStudentUI(studentController, dc, gc, uc):
        ident = MainMenu.readUserCommand("Enter the student's ID to be removed:")
        uc.newOperation()
        studentController.removeStudent(ident)
        gc.removeStudent(ident)
        print("Student with ID", ident, "removed successfully!")
    
    @staticmethod
    def updateStudentUI(studentController, dc, gc, uc):
        ident = MainMenu.readUserCommand("Enter the student's ID:")
        newName = MainMenu.readUserCommand("Enter the new name of the student:")
        uc.newOperation()
        studentController.updateStudent(ident, newName)
        print("Student updated successfully!")          
        
    @staticmethod   
    def searchStudentByNameUI(studentController, dc, gc, uc):    
        name = MainMenu.readUserCommand("Enter student's name to search for:")
        print(studentController.searchForName(name))
        
    @staticmethod   
    def searchStudentByIdUI(studentController, dc, gc, uc):
        ident = MainMenu.readUserCommand("Enter student's id to search for:")
        print(studentController.searchForId(ident))
        
    @staticmethod   
    def listStudentsUI(studentController, dc, gc, uc):
        print(studentController)
    
    @staticmethod
    def addDisciplineUI(sc, disciplineController, gc, uc):
        ident = MainMenu.readUserCommand("Enter discipline's ID:")
        name = MainMenu.readUserCommand("Enter discipline's name:")
        uc.newOperation()
        disciplineController.addDiscipline(ident, name)
        print("Discipline added successfully!")
    
    @staticmethod
    def removeDisciplineUI(sc, disciplineController, gc, uc):
        ident = MainMenu.readUserCommand("Enter the discipline's ID to be removed:")
        uc.newOperation()
        disciplineController.removeDiscipline(ident)
        gc.removeDiscipline(ident)
        print("Discipline with ID", ident, "removed successfully!")
    
    @staticmethod
    def updateDisciplineUI(sc, disciplineController, gc, uc):
        ident = MainMenu.readUserCommand("Enter the discipline's ID:")
        newName = MainMenu.readUserCommand("Enter the new discipline's name:")
        uc.newOperation()
        disciplineController.updateDiscipline(ident, newName)
        print("Discipline updated successfully!")
        
    @staticmethod    
    def searchDisciplineByNameUI(sc, disciplineController, gc, uc):
        name = MainMenu.readUserCommand("Enter discipline's name to search for:")
        print(disciplineController.searchForName(name))
        
    @staticmethod     
    def searchDisciplineByIdUI(sc, disciplineController, gc, uc):
        ident = MainMenu.readUserCommand("Enter discipline's id to search for:")
        print(disciplineController.searchForId(ident))
        
    @staticmethod
    def listDisciplinesUI(sc, disciplineController, gc, uc):
        print(disciplineController)
    
    @staticmethod
    def addGradeUI(gradeController, uc):
        disciplineId = MainMenu.readUserCommand("Enter discipline's ID:")
        studentId = MainMenu.readUserCommand("Enter student's ID:")
        gradeValue = MainMenu.readUserCommand("Enter the grade value:")
        uc.newOperation()
        gradeController.addGrade(disciplineId, studentId, [gradeValue])
        print("Grade added successfullly!")
        
    @staticmethod
    def enrollStudentUI(gradeController, uc):
        studentId = MainMenu.readUserCommand("Enter the student's ID:")
        disciplineId = MainMenu.readUserCommand("Enter the disciplne's ID:")
        uc.newOperation()
        gradeController.enrollStudent(disciplineId, studentId, [0])
        print("Student enrolled successfully!")
        
    @staticmethod
    def sortAscUI(gradeController, uc):
        print("1st statistic")
        l = gradeController.sortAsc()
        print(gradeController.listToString(l))
    
    @staticmethod
    def sortGradesDescUI(gradeController, uc):
        print("1st statistic")
        l = gradeController.sortDescGrades()
        print(gradeController.listToString(l))
        
    @staticmethod    
    def failingDisciplineUI(gradeController, uc):
        print("2nd statistic")
        l = gradeController.failingDisciplines()
        print(gradeController.listToString(l))
        
    
    @staticmethod
    def aggregateAverageUI(gradeController, uc):
        print("3rd statistic")
        l = gradeController.aggregateAverage()
        print(gradeController.listToString2(l))
        
    @staticmethod
    def descOrderDiscGrade(gradeController, uc):
        print("4th statistic")
        l = gradeController.aggregateAverageForDisciplines()
        print(gradeController.listToString3(l))
    
        
    @staticmethod
    def listGradesUI(gradeController, uc):
        print(gradeController)
        
    @staticmethod
    def tryIt(gradeController):
        l = gradeController.tryIt()
        for x in l:
            print(x)
            
   
    @staticmethod
    def doUndo(uc):
        uc.undo()
        
    @staticmethod   
    def doRedo(uc):
        uc.redo()
        
    
    def mainMenu(self):
        students = {"1": MainMenu.addStudentUI, "2": MainMenu.removeStudentUI,
                    "3": MainMenu.updateStudentUI, "4": MainMenu.listStudentsUI, "5" : MainMenu.searchStudentByNameUI, "6" : MainMenu.searchStudentByIdUI,
                    "7": MainMenu.addDisciplineUI, "8": MainMenu.removeDisciplineUI, "9": MainMenu.updateDisciplineUI, "10": MainMenu.listDisciplinesUI,
                    "11" : MainMenu.searchDisciplineByNameUI, "12" : MainMenu.searchDisciplineByIdUI}
        grades = {"13" : MainMenu.enrollStudentUI, "14": MainMenu.addGradeUI, "15": MainMenu.listGradesUI, "16" : MainMenu.sortAscUI, "17" : MainMenu.sortGradesDescUI, "18" : MainMenu.failingDisciplineUI, "19" : MainMenu.aggregateAverageUI, "20" : MainMenu.descOrderDiscGrade}
        while True:
            print("Type help to see the menu!")
            command = MainMenu.readUserCommand()       
            try:
                if command == "help":
                    print(MainMenu.getMenu())
                elif command == "0":
                    return
                elif command == "21":
                    MainMenu.doUndo(self._undoController)
                elif command == "22":
                    MainMenu.doRedo(self._undoController)
                elif command == "23":
                    MainMenu.tryIt(self._gradeController)
                else:
                    if command in students:
                        try:
                            students[command](
                                self._studentController, self._disciplineController, self._gradeController, self._undoController)
                            self._gradeController.updateRepositories(self._studentController._studentRepo, self._disciplineController._disciplineRepo)
                        except RepositoryException as msg:
                            print(msg)
                        except ValidatorException as msg:
                            print(msg)
                
                    elif command in grades:
                        try:
                            grades[command](self._gradeController, self._undoController)
                        except RepositoryException as msg:
                            print(msg)
                        except ValidatorException as msg:
                            print(msg)
    
                    else:
                        print("Invalid command!")
            except IndexError:
                print("Enter not allowed!")
    
    @staticmethod
    def getMenu():
        s = "Available commands(Note id cannot be changed once it's chosen):\n"
        s += "\t 0 - exit\n"
        s += "Student:\n"
        s += "\t 1 - add student\n"
        s += "\t 2 - remove student\n"
        s += "\t 3 - update student\n"
        s += "\t 4 - list students\n"
        s += "\t 5 - search students by name\n"
        s += "\t 6 - search students by id\n"
        
        s += "Discipline:\n"
        s += "\t 7 - add discipline\n"
        s += "\t 8 - remove discipline\n"
        s += "\t 9 - update discipline\n"
        s += "\t 10 - list disciplines\n"
        s += "\t 11 - search disciplines by name\n"
        s += "\t 12 - search disciplines by id\n"
        
        s += "Grades:\n"
        s += "\t 13 - enroll student at a discipline\n"
        s += "\t 14 - add grades to a student at a given discipline\n"
        s += "\t 15 - list grades\n"

        s += "Statistics:\n"
        s += "\t 16 - show students aplhabeticaly\n"
        s += "\t 17 - show students by their grades in descending order\n"
        s += "\t 18 - show students who are failing ( avg grade < 5)\n"
        s += "\t 19 - aggregate average (the average between their average grades per discipline)\n"
        s += "\t 20 - general average at all disciplines\n"
        s += "Undo\Redo:\n"
        s += "\t 21 - Undo\n"
        s += "\t 22 - Redo"
        
        return s


    



