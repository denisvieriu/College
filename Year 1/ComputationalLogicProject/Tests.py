'''
Created on Dec 6, 2016

@author: Denis
'''
from Controller import *

def testAddition():
    assert addition("ABCDE", "789F", 16) == "B357D"
    assert addition(24312, 1234, 5) == "31101"
    assert addition("00010010", "00010010", 2) == "00100100"
    assert addition(12, 12, 16) == "24"

def testSubstraction():
    assert substraction("C0D91", "BEDF", 16) == "B4EB2"
    assert substraction(304005, 25346, 7) == "245326"
    assert substraction("11110011", "00010010", 2) == "11100001"
    assert substraction("F3", "12", 16) == "E1"


def testMultiplication():
    assert multiplication(34567, 5, 8) == "217523"
    assert multiplication("D8BF", "A", 16) == "87776"
    assert multiplication("11001100", 1, 2) == "11001100"


def testDivision():
    assert division(5103, 7, 10) == (0, "0729")  # (reaminder, quotient)
    assert division(5234, 4, 6) == (2, "1205")
    assert division("B9FE", "A", 16) == (4, "1299")
    assert division("A5B", 8, 16) == (3, "14B")
    assert division(8, 2, 10) == (0, "4")
    assert division("20101", "2", "3") == (0, "10012")
    # assert division("1FED0205", 9, 16) == (2, "38C1CAB")

def testSubstitutionMethod():
    assert substitutionMethod(2034, 5, 7) == "533"
    assert substitutionMethod(354, 6, 8) == "216"
    assert substitutionMethod(167, 8, 5) == "434"
    assert substitutionMethod(1345, 7, 16) == "20B"
    
def testSuccessiveDivisions():
    assert successiveDivisions("A5B", 16, 8) == "5133"  
    assert successiveDivisions(123, 4, 2) == "11011" 
    assert successiveDivisions(4271, 8, 6) == "14201"
    assert successiveDivisions("1A2", 16, 8) == "642"
  
def testConvertFromBaseToAnother():  # using 10 as an intermediate base
    assert convertFromBaseToAnother("A5B", 16, 8) == "5133"
    assert convertFromBaseToAnother(123, 4, 2) == "11011" 
    assert convertFromBaseToAnother(4271, 8, 6) == "14201"
    assert convertFromBaseToAnother("1A2", 16, 8) == "642"
    assert convertFromBaseToAnother(2034, 5, 7) == "533"
    assert convertFromBaseToAnother(354, 6, 8) == "216"
    
def testRapidConversions():
    assert rapidConversion("3201", 4, 2) == "11100001"
    assert rapidConversion("7205", 8, 2) == "111010000101"
    assert rapidConversion("1001100011010111", 2, 8) == "114327"
    assert rapidConversion("0111000011001010", 2, 4) == "13003022"
    assert rapidConversion("001100010101100111001010", 2, 16) == "3159CA"
    
    
def runAllTests():
    testAddition()
    testSubstraction()
    testMultiplication()
    testDivision()
    testSubstitutionMethod()
    testSuccessiveDivisions()
    testConvertFromBaseToAnother()
    testRapidConversions()
    

