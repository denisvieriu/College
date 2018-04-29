'''
Created on Oct 16, 2016

@author: Denis
'''


def representsInt(number):
    '''
    '''
    try:
        number = int(number)
        return True
    except ValueError:
        return False


def addComplexNumber(l, complexNumber):
    '''
    '''
    complexNumber = getRealAndImaginaryPart(complexNumber)
    if complexNumber != 0:
        l.append(complexNumber)
        return ""
    else:
        return "Invalid format! The number was not under the following form: add add <number>"


def insertComplexNumber(l, complexNumber, pos):
    '''

    '''
    complexNumber = getRealAndImaginaryPart(complexNumber)
    if complexNumber != 0:
        l.insert(pos, complexNumber)
        return ""
    else:
        return "Invalid format! The number was not under the following form: insert <number> at <position>"


def getRealAndImaginaryPart(complexNumber):
    '''
    '''
    ok = False
    realAndImaginary = complexNumber.split('+')
    if len(realAndImaginary) == 2:
        # 0 means the number is under the format: "a+bi"
        realAndImaginary.append(0)
        ok = True
    if not ok:
        realAndImaginary = complexNumber.split('-')
    if len(realAndImaginary) == 2:
        # 1 means the number is under the format: "a-bi"
        realAndImaginary.append(1)
        ok = True
    if ok == True:
        imaginaryPart = realAndImaginary[1]
        if imaginaryPart[len(imaginaryPart) - 1] != 'i':
            return 0
        imaginaryPart = imaginaryPart.split('i')
        realAndImaginary[1] = imaginaryPart[0]
        if not representsInt(realAndImaginary[0]) or not representsInt(realAndImaginary[1]):
            return 0
        realAndImaginary[0] = int(realAndImaginary[0])
        realAndImaginary[1] = int(realAndImaginary[1])
        return realAndImaginary
    else:
        return 0


def test():
    l = []
    l.append([1, 2, 0])
    l.append([1, 2, 3])
    print(insertComplexNumber(l, "1+2i", 2))
    print(l)


test()
