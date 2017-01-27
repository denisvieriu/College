'''
Created on Jan 26, 2017

@author: Denis
'''

class UI:
    def __init__(self, controller): 
        self._controller = controller
        
    
    
    @staticmethod
    def startGame(controller):
        return controller.startGame()
    
    
    def mainMenu(self):
        print("Type 1 to play the game")
        pressed1 = False
        while True:
 
            command = input("Enter command: ")
            if command == "1" and not pressed1:
                points, initialString, shuffledString = UI.startGame(self._controller)
                pressed1 = True
                print("Type word1 letter 1 word2 letter2 to swap")
            
            if pressed1 == True:
                print(UI.listToString(shuffledString), points)
                if initialString == shuffledString:
                    print("You won!") 
                    return
                elif points == 0:
                    print("You lost!")
                else:
                    word1 = int(input("Enter first word: "))
                    letter1 = int(input("Enter first letter"))
                    word2 = int(input("Enter second word"))
                    letter2 = int(input("Enter second letter"))
                    self._controller.shuffle(shuffledString, word1, letter1, word2, letter2)
                    
                
            else:
                print("Invalid command")
                
    @staticmethod
    def listToString(string):
        s = ""
        for x in string:
            s = s + "".join(x) + ' '
        return s        
