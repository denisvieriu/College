'''
Created on Nov 25, 2016

@author: Denis
'''

from initialization.Initialization import initAll
from UI.UI import MainMenu
from GUI.mainGUI import AllInOne

undoController, studentController, disciplineController, gradeController = initAll()



def chooseMenu():
    s = menu()
    print(s)
    v = str(input())
    while True:
        if v == '1':
            ui = MainMenu(studentController, disciplineController, gradeController, undoController)
            ui.mainMenu()
            return 
        elif v == '2':
            ui = AllInOne(undoController, studentController, disciplineController, gradeController)
            ui.startUI()
            return 
        else:
            print("Invalid command!\n")
            print(s)
            v = str(input())

    
    
def menu():
    s = "Available menus:\n"
    s += "\t 1 - menu based\n"
    s += "\t 2 - GUI based\n"
    return s
        
chooseMenu()
