'''
Created on Dec 30, 2016

@author: Denis
'''

import uuid
import random
from domain.Student import Student
from domain.Student import StudentValidator
from controller.StudentController import StudentController
from repository.FileRepository import FileRepository

class StudentRandomDataGenerator:
    def __init__(self, studentController):
        self._studentController = studentController
   
    def addData(self):
        with open(r"C:\Users\Denis\Desktop\random data gener\students.txt", "r") as f:
            lines = f.readlines()
            ident = uuid.uuid4().hex  # get random id
            lenLines = len(lines)
            studentNr = random.randint(0, lenLines)
            try:
                self._studentController.addStudent(str(ident), lines[studentNr])
            except:
                pass

sv = StudentValidator()
sr = FileRepository(r"C:\Users\Denis\Desktop\random data gener\blabla.txt", Student)
sc = StudentController(sr, sv)            
s = StudentRandomDataGenerator(sc)
s.addData()

            
