'''
Created on Jan 10, 2017

@author: Denis
'''


from copy import deepcopy


'''
Method user to sort:    Heap Sort
Analysis of complexity: 
    ;we know the height of a heap is h = [log2 N] , N belonging to [2^h, 2^(h+1)-1]
    ;we begin with building the heap, then we extract the max ( which will be found at pos 1 )
    ;and we move it to the end of the vector, decreasing N with one unit, then we re-build the heap(in case of ascending order)
    ;the above example was a max-heap, min-heap works the same way as explained before
    
Time Complextity:
    ;first step: extract the top of the heap O(1) then rebuild it O(log N) = O(1) + O(log N) = O(log N)
    ;then we repeat the process for all elements of the heap ( N times ) --> O(N * log N)

Memory Complexity:
    ;the program doesn't use any additional memory (we just mute mute max elements to last positions --> Memory comp. O(1))
    
'''
class MyStructure:
    def __init__(self):
        self._data = []
    
    def add(self, value):
        # Add data to the structure
        self._data.append(value)
        
        
    def __iter__(self):
        # Creates an iterator
        self._iterPoz = 0
        return self
    
    def __next__(self):
        # Iterates over the next element within the list, if the next element doesn't exists raise ValueError
        if self._iterPoz >= len(self._data):
            raise StopIteration()
        rez = self._data[self._iterPoz]
        self._iterPoz += 1
        return rez
    
         
    def __delitem__(self, key):
        # Delete an item from the structure
        del self._data[key]
        
    def __setitem__(self, key, value):
        self._data[key] = value
    
    def __getitem__(self, index):
        return self._data[index]
    
    @staticmethod
    def father(nod):
        return nod // 2
    
    @staticmethod
    def left_son(nod):
        return nod * 2

    @staticmethod
    def right_son(nod):
        return nod * 2 + 1


    def sift(self, n, k, lt):
        '''
        Sift method - checks if the father has the bigger value in his subtree
        Complexity - O(log N) = worst case <-> height of the tree = log N
        
        Input   : n - nr. of elements withing the structure
                  k - the element to be checked
                  lt - the function between to nods
        '''
        son = 0
        firstRun = True
        while son or firstRun == True:  # First run must always be checked
            firstRun = False    
            son = 0  # We assume everything is well-organised in our tree
            if MyStructure.left_son(k) <= n:  # We pick a son bigger than father
                son = MyStructure.left_son(k)   
                if MyStructure.right_son(k) <= n and lt(self._data[MyStructure.right_son(k)], self._data[MyStructure.left_son(k)]) == False:
                    son = MyStructure.right_son(k)  # Compare between right son and left son 
                if lt(self._data[son], self._data[k]) == True:  # if the son selected is smaller than father, it means it's good
                    son = 0
            if son:  # interchange the values
                    self._data[k], self._data[son] = self._data[son], self._data[k]
                    k = son
                            
                            
    def build_H(self, n, lt):
        for i in range(n // 2, 0, -1):
            MyStructure.sift(self, n, i, lt)

    def H_sort(self, lt):
        n = len(self._data)
        self._data[1:len(self._data) + 1] = self._data
        self._data[0] = 0
        MyStructure.build_H(self, n, lt)
        for i in range(n, 1, -1):
            self._data[1], self._data[i] = self._data[i], self._data[1]
            MyStructure.sift(self, i - 1, 1, lt)
        self._data.pop(0)
        return self._data
        

    def sort(self, key):
        pass
    
    
    def filter(self, function):
        n = len(self._data)
        newDataStr = MyStructure()
        for x in self._data:
            if function(x):
                newDataStr.add(x)
        return newDataStr
    
    
    
    def __len__(self):
        return len(self._data)
    
    
'''
def testInCollection():
    a = MyStructure()
    
    s = Student("2", "Ion")
    a.add(s)
    s = Student("1", "Zlex")
    a.add(s)
    
    
    def function(param):
        return param.name == "Ion"
    
    s = (a.filter(function))    
    for x in s:
        print(x)
    
    
    def lt(first, second):
        return first.name < second.name
    l = a.H_sort(lt)
    for x in l:
        print(x)
    
    
    
testInCollection()
'''
