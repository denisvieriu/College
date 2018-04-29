'''
Created on Dec 4, 2016

@author: Denis
'''


import math




maxBase = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F']
# maxBase = all the numbers in base 16 (used to transform single digits in base 10 , e.g : for decimal part at addition )

def makeString(firstNr, secondNr):
    return str(firstNr), str(secondNr)

def isPower (num, base):
    '''
    Checks if num is power of base using log
    '''
    if base == 1 and num != 1: return False
    if base == 1 and num == 1: return True
    if base == 0 and num != 1: return False
    power = int (math.log (num, base) + 0.5)
    return base ** power == num

def checkBase(base):
    '''
    Checks if the base given is valid
    Input  - base : representing the base read by the user
    Output -
    Raise ValueError if the given base is invalid
    '''
    try:
        base = int(base)
        if base < 0 or base > 16:
            raise ValueError("0<=base<=16")
        return base
    except:
        raise ValueError("Invalid base")
    


def addition(firstNr, secondNr, base):
    '''
    Adds 2 numbers in a specified base 
    Input : firstNr - the first number read by the user
            secondNr - the second number read by the user
            base - the specified base to perform the addition
    Output : newNr - a new number representing the addition of the 2 numbers
    0<=base<=16 : raises ValueError otherwise
    '''
    base = checkBase(base)
    firstNr, secondNr = makeString(firstNr, secondNr)  # converting the given numbers into strings
    maxChars = max(len(firstNr), len(secondNr))
    # padding(adding) insignificant 0s in the left part of 1st number
    firstNr = firstNr.rjust(maxChars, '0')
    # padding insignificant 0s in the left part of the 2nd number
    secondNr = secondNr.rjust(maxChars, '0')
    newNr = []
    quotient = remainder = 0
    # goes from len(n) to 0 (reversed order)
    for i in range(maxChars - 1, -1, -1):
        # adding the digit found on index i from firstNr, secondNr and the quotient
        decimal = maxBase.index(firstNr[i]) + maxBase.index(secondNr[i]) + quotient
        quotient = decimal // base
        remainder = decimal % base
        newNr.append(remainder)
    # if the last performed quotient it's not 0 we have to add it at final
    if quotient != 0:
        newNr.append(quotient)
    # we added the digit in reversed order in our number, thus we reverse it
    newNr.reverse()
    # returning the quotient as a string using join function
    return("".join([maxBase[x] for x in newNr]))  # returning the number with the correct format(in respective base)


def convertToBase10(number, sourceBase):
    '''
    Converts a number from a base i, 0<=i<=16, to base 10
    Input : number - the number to be converted in base 10
            sourceBase - the inital base of the number
    Output : newNumber - an integer representing the number in base 10 
    '''
    sourceBase = checkBase(sourceBase)
    number = str(number)
    newNumber = 0
    for i in reversed(range(len(number))):
        newNumber += (maxBase.index(number[i]) * (sourceBase ** (len(number) - i - 1)))  # we start adding in newNumber from right to left
    return newNumber


def convertFromBaseToAnother(number, sourceBase, destinationBase):  # using 10 as intermediate base
    '''
    Converts a number from a base to another base using base 10 as intermediate base
    After the initial number is converted to base 10, we perform divisions untill the quotient will be 0
    Input  : number - the number to be converted
             sourceBase - the sourceBase of the number
             destinationBase - the destination base where the number needs to be converted
    Output : newNr - representing the new number in destination base
    ''' 
    sourceBase = checkBase(sourceBase)
    destinationBase = checkBase(destinationBase)
    number = str(number)
    number = convertToBase10(number, sourceBase)  # converting the initial number in base 10
    newNr = []
    while number != 0:  # performing divisions until quotient equals 0 (number = quotient) 
        remainder = number % destinationBase
        number = number // destinationBase
        newNr.append(remainder)
    newNr.reverse()
    if len(newNr) == 0:
        return '0'
    return("".join([maxBase[x] for x in newNr]))


def substraction(firstNr, secondNr, base):
    '''
    Substract 2 numbers in a given base
    Input  : firstNr - the first number
             secondNr - the second number
             base - the base of the 2 numbers
    Output : newNr - representing a new number made of the 2 numbers substracted
    '''
    
    first = convertToBase10(firstNr, base)
    second = convertToBase10(secondNr, base)
    if second > first:
        raise ValueError("Second number can't be bigger than first number!")
    base = checkBase(base)
    firstNr, secondNr = makeString(firstNr, secondNr)   
    maxChars = max(len(firstNr), len(secondNr))  # will use max chars to pad with insignificant 0's in the left part of the number
    firstNr = firstNr.rjust(maxChars, '0')  # padding insignificant 0s to the left part of the number        
    secondNr = secondNr.rjust(maxChars, '0')
    firstNr = list(firstNr)  # transforming firstNr in a list
    secondNr = list(secondNr)  # transforming secondNr in a list
    quotient = remainder = 0
    newNr = []
    for i in range(maxChars - 1, -1, -1):
        if maxBase.index(firstNr[i]) < maxBase.index(secondNr[i]):
            '''
            checking if the digit from above is smaller than the digit from below
            ---> so we have to go to the left part of the number, making all 0s = base - 1, and decreasing with 1 the first non-zero element
            ''' 
            j = i - 1
            while j != 0 and firstNr[j] == '0':
                firstNr[j] = maxBase[base - 1]
                j -= 1
            firstNr[j] = maxBase[maxBase.index(firstNr[j]) - 1]
            number = "1" + firstNr[i]  # adding 1 in front of the number, because we borrowed it from a non-zero element
            number = convertToBase10(number, base) - maxBase.index(secondNr[i]) + quotient
            quotient = number // base  # getting the quotient
            remainder = number % base  # getting the remainder
            newNr.append(remainder)
        else:
            '''
            the digit from above is higher than the digit from below so we just substract them
            '''
            number = maxBase.index(firstNr[i]) - maxBase.index(secondNr[i]) + quotient
            quotient = number // base
            remainder = number % base
            newNr.append(remainder)
    newNr.reverse()
    return("".join([maxBase[x] for x in newNr]))


def multiplication(firstNr, digit, base):  # multiplication by 1 digit
    '''
    Multiplies a number by one digit in a given base.
    Input : firstNr - the first number read
            digit - the digit to multiply by
            base - the base where we'll perform the operations
    Ouput : newNr = (firstNr * digit) in respective base   (returned as string)
    Raise ValueError - if the base is invalid
                     - len(digit) > 1
    '''
    
    base = checkBase(base)  # checks if the given base is valid
    # converts the number in string for easir access to the digits
    firstNr, digit = makeString(firstNr, digit)
    if len(digit) > 1:
        raise ValueError("Digit bigger than 1!")
    maxLen = len(firstNr)
    digit = maxBase.index(digit)  # converting the digit in base 10
    quotient = remainder = 0
    newNr = []
    # we're going from the last element of the item to the first(and
    # multiplying every digit from 1st number with the 2nd number
    for i in range(maxLen - 1, -1, -1):
        number = quotient + digit * maxBase.index(firstNr[i])
        quotient = number // base
        remainder = number % base
        newNr.append(remainder)
    if quotient != 0:  # if we have an carry we add it
        newNr.append(quotient)
    # we added the numbers in list from right to left, so we need to reverse
    # it now
    newNr.reverse()
    return("".join([maxBase[x] for x in newNr]))


# multiplication between 2 numbers ( second number has more than 1 digit )
def mul(firstNr, secondNr, base):
    '''
    Same as multiplication, but the second number has more than 1 digit
    We'll multiply first number with each digit from the second number, then adding them
    Input : firstNr - the first number read
            digit - the digit to multiply by
            base - the base where we'll perform the operations
    Ouput : newNr = (firstNr * digit) in respective base   (returned as string)
    '''
    base = checkBase(base)
    firstNr, secondNr = makeString(firstNr, secondNr)
    if(len(firstNr) < len(secondNr)):
        firstNr, secondNr = secondNr, firstNr
    maxChars = len(secondNr)
    number = 0
    for i in range(maxChars - 1, -1, -1):
        if i == maxChars - 1:
            number = multiplication(firstNr, secondNr[i], base)
            continue
        # we add 0 in the right part of the number to add it correctly, like a
        # math multiplication
        number = addition(number, multiplication(firstNr, secondNr[i], base) + "0" * (maxChars - i - 1), base)
    return number


def division(firstNr, digit, base):
    '''
    Performs a division by one digit between firstNr and digit
    Input :  firstNr - first number read
             digit - second number read
             base - the base of the 2 numbers
    Output : newNr - representing the quotient of the number
             remainder 
    '''
    firstNr, digit = makeString(firstNr, digit)
    maxLen = len(firstNr)
    digit = maxBase.index(digit)
 
    quotient = remainder = 0
    newNr = []
    for i in range(0, maxLen):
        number = maxBase[remainder] + firstNr[i]
        number = convertToBase10(number, base)
     
        quotient = number // digit
        remainder = number % digit
        
        newNr.append(quotient)

    # the only thing left is to return the remainder and quotient 
    # return (remainder, quotient)
    return remainder, ("".join([maxBase[x] for x in newNr]))


# implemented for sourceBase > destinationBase and sourceBase < destinationBase, both cases work
def substitutionMethod(number, sourceBase, destinationBase):
    '''
    Calculation is performed in the destination base.
    -    all the digits from the source representation are converted into the destination base
    -    the base b is converted into base h
    This method converts a number from a base to another.
    Input  : number - the number to convert ( represented as a string )
             sourceBase - the initial base of the number
             destinationBase - the base where the number need to be converted
    Output : newNumber - representing the original number converted in destination base
    Raises ValueError if the base is invalid
    '''
    number = str(number)
    sourceBase = checkBase(sourceBase)  # checks if the bases are valid
    destinationBase = checkBase(destinationBase)
    newNumber = 0
    n = len(number)
    # transforming the source base into destination base
    sourceBaseinDestinationBase = convertFromBaseToAnother(sourceBase, sourceBase, destinationBase)
    for i in range(0, n):
        if i == 0:
            newNumber = convertFromBaseToAnother(number[n - i - 1], sourceBase, destinationBase)
            continue
        power = sourceBaseinDestinationBase
        for j in range(1, i):
            power = mul(power, sourceBaseinDestinationBase, destinationBase)
        digit = convertFromBaseToAnother(number[n - i - 1], sourceBase, destinationBase)
        newNumber = addition(newNumber, mul(digit, power, destinationBase), destinationBase)
    return newNumber



def successiveDivisions(number, sourceBase, destinationBase):
    '''
    Calculation is made in the source base.
    -    it is the generalization of the method of successive divisions/multiplications presented before for h=10.
    -    in the general case - calculation is in an arbitrary source base b
    Input  :  number - the number to convert ( represented as a string )
              sourceBase - the initial base of the number
              destinationBase - the base where the number need to be converted
    Output : newNr - representing the number converted from sourceBase to destinationBase
    '''
    newNr = []
    remainder, number = division(number, destinationBase, sourceBase)
    newNr.append(remainder)
    while number != '0':
        try:
            number = int(number)
            if number == 0:  # if the number ( which in this case is the same with quotient ) became 0, we stop
                break
        except:
            pass
        remainder, number = division(str(number), destinationBase, sourceBase)
        
        newNr.append(remainder)
    newNr.reverse()
    return ("".join([maxBase[x] for x in newNr])) 


def rapidConversion(number, sourceBase, destinationBase):
    '''
    Converts a number using rapid conversions
    Bases p,q belong to {2,4,8,16}
    Input  : number - the number to be converted
             sourceBase must belong to {2,4,8,16}
             destinationBase must belong to {2,4,8,16}
    Output : newNr - representing the number converted
    Raises ValueError if the bases are not power of 2 
    '''
    sourceBase = checkBase(sourceBase)
    destinationBase = checkBase(destinationBase)
    if isPower(sourceBase, 2) == False or isPower(destinationBase, 2) == False:  # checks if bases are power of 2
        raise ValueError("Invalid base!")
    binary = convertFromBaseToAnother(number, sourceBase, 2)  # transforms the initial string in binary
    power = int (math.log (destinationBase, 2) + 0.5)  # gets the power of the second number(e.g : 1,2,3,4)
    n = len(binary) 
    newNr = []
    for i in range(n , -1, -power):  # from right to left we make groups of power
        newNr.append(convertFromBaseToAnother(binary[i - power:i], 2, destinationBase))
    j = len(newNr) - 1  
    # deleting the first insignificant 0 from the number 
    # ( added when converting from right to left, if len(binary) is not a multiple of power, case when we can't form an exact number of groups)
    while newNr[j] == '0':   
        j -= 1
    newNr[:] = newNr[:j + 1]  # insignificant 0 deleted
    if i != 0:
        newNr.append(convertFromBaseToAnother(binary[0:i], 2, destinationBase))  # add the last group of digits from binary(the leftmost one) 
    newNr.reverse()
    return("".join([str(x) for x in newNr])) 


