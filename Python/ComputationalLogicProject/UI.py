'''
Created on Dec 4, 2016

@author: Denis
'''

from Controller import *

def readUserCommand(msg=""):
    return input(msg).upper()

def additionUI():
    firstNr = readUserCommand("Enter the first number:")
    secondNr = readUserCommand("Enter the second number:")
    base = readUserCommand("Enter the base:")
    number = addition(firstNr, secondNr, base)
    print(number)
    
def substractionUI():
    firstNr = readUserCommand("Enter the first number:")
    secondNr = readUserCommand("Enter the second number:")
    base = readUserCommand("Enter the base:")
    number = substraction(firstNr, secondNr, base)
    print(number)

def multiplicationUI():
    firstNr = readUserCommand("Enter the first number:")
    digit = readUserCommand("Enter the digit to multiply by:")
    base = readUserCommand("Enter the base:")
    number = multiplication(firstNr, digit, base)
    print(number)
    
def divisionUI():
    firstNr = readUserCommand("Enter the first number:")
    digit = readUserCommand("Enter the digit to divide by:")
    base = readUserCommand("Enter the base:")
    remainder, number = division(firstNr, digit, base)
    print("The quotient is:", number)
    

def substitutionMethodUI():
    number = readUserCommand("Enter the number:")
    sourceBase = readUserCommand("Enter the base of the number:")
    destinationBase = readUserCommand("Enter the destination base to convert to number:")
    number = substitutionMethod(number, sourceBase, destinationBase)
    print(number)

def successiveDivisionsUI():
    number = readUserCommand("Enter the number:")
    sourceBase = readUserCommand("Enter the base of the number:")
    destinationBase = readUserCommand("Enter the destination base to convert to number:")
    number = successiveDivisions(number, sourceBase, destinationBase)
    print(number)

def convertFromBaseToAnotherUI():
    number = readUserCommand("Enter the number:")
    sourceBase = readUserCommand("Enter the base of the number:")
    destinationBase = readUserCommand("Enter the destination base to convert to number:")
    number = convertFromBaseToAnother(number, sourceBase, destinationBase)
    print(number)


def rapidConversionUI():
    number = readUserCommand("Enter the number:")
    sourceBase = readUserCommand("Enter the base of the number:")
    destinationBase = readUserCommand("Enter the destination base to convert to number:")
    number = rapidConversion(number, sourceBase, destinationBase)
    print(number)


def mainMenu():
    print(author())
    functions = {"1":additionUI, "2":substractionUI, "3":multiplicationUI, "4":divisionUI, "5":substitutionMethodUI, "6":successiveDivisionsUI, "7":convertFromBaseToAnotherUI, "8":rapidConversionUI}
    while(True):
        print(menu())
        command = readUserCommand()
        if command == "0":
            return
        if command in functions:
            try:
                functions[command]()
            except ValueError as msg:
                print(msg)
        else:
            print("Invalid command!")
            
            
def author():
    s = "********************************************************************\n"
    s += "*** Project realised by:  | Vieriu Denis - Gabriel | Group 915 | ***\n"
    s += "********************************************************************\n"
    return s 


def menu():
    s = "Availabe commands:\n"
    s += "\t 0 - exit\n"
    s += "\t 1 - add 2 numbers in a base\n"
    s += "\t 2 - substract 2 numbers in a base\n"
    s += "\t 3 - multiply a number by a digit in a base\n"
    s += "\t 4 - divide a number by a digit in a base\n"
    s += "\t 5 - convert a number from a base to another base using substitution method\n"
    s += "\t 6 - convert a number from a base to another base using successive divisions method\n"
    s += "\t 7 - convert a number from a base to another base using 10 as intermediate base\n"
    s += "\t 8 - rapid conversions(p,q(bases) must belong to {2,4,8,16})"
    return s

