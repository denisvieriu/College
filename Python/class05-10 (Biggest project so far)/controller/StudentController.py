'''
Created on Nov 19, 2016

@author: Denis
'''

from domain.Student import Student
# from repository.Repository import Repository


class StudentController:

    def __init__(self, studentRepo, studentValidator):
        '''
        Student class constructor
        studentRepo - the student's repository
        studentValidator - used for validating a new added student
        '''
        self._studentRepo = studentRepo
        self._studentValidator = studentValidator

    def addStudent(self, ident, name):
        '''
        Saves a student with an id and a name to the student repository
        Input  :  ident - the student's id
                  name  - the student's name
        Output : the student
        Error : if the data entered is invalid
        '''
        s = Student(ident, name)
        self._studentValidator.validate(s)
        self._studentRepo.store(s)
        return s

    def removeStudent(self, ident):
        '''
        Removes a student based on his ID
        ident - the id of the student 
        '''
        self._studentRepo.remove(ident)
     
    def updateStudent(self, ident, name):
        '''
        Updates a student(only his name)
        ident - the id of the student
        name - name of the student
        '''
        entity = self._studentRepo[ident]
        if name != None:
            entity.name = name
        self._studentRepo.update(entity)
        
    def searchForName(self, name):
        '''
        Partial string matching(search for a student after his name, the search is case insensitive)
        name - the name to search for ( can't be empty )
        '''
        _entity = []
        s = Student(None, name)
        self._studentValidator.validate(s)
        for student in self._studentRepo.getAll():
            if name.lower() in student.name.lower():
                _entity.append(str(student))
        return _entity
                
    def searchForId(self, ident):
        '''
        Partial string matching(search for a student after his id, the search is case insensitive)
        id - the id to search for ( can't be empty )
        '''
        _entity = []
        if ident == None or ident == "":
            return(None)
        for student in self._studentRepo.getAll():
            if ident.lower() in student.getId.lower():
                _entity.append(str(student))
        return _entity

    def getStudents(self):
        '''
        Return the student's repository
        '''
        return self._studentRepo.getAll()

    def __str__(self):
        '''
        Owerwrite the str
        '''
        return str(self._studentRepo)
    
    def __len__(self):
        return len(self._studentRepo.getAll())

'''
def testStudentController():
    studentRepository = Repository()
    studentValidator = StudentValidator()
    sc = StudentController(studentRepository, studentValidator)
    sc.addStudent(12, "Ana")
    sc.updateStudent(12, "Bere")
    assert len(sc.getStudents()) == 1
    sc.addStudent(16, "George")
    assert len(sc.getStudents()) == 2
    try:
        sc.addStudent(16, "Sam")
        assert False
    except:
        assert True
    sc.removeStudent(12)
    assert len(sc.getStudents()) == 1
    try:
        sc.removeStudent(5)
        assert False
    except:
        assert True
    
    
# testStudentController()
'''