'''
Created on Nov 27, 2016

@author: Denis
'''



from tkinter import *
from tkinter import messagebox

class AllInOne:
    def __init__(self, undoController, studentController, disciplineController, gradeController):
        self.undoController = undoController
        self.studentController = studentController
        self.disciplineController = disciplineController
        self.gradeController = gradeController
        self.customFont = ("Courier", 17, "bold")
        
        
    def startUI(self):
        self.tk = Tk()
        self.tk.title("GUI")
        self.tk.configure(background="#a1dbcd")
        
        ###############################################################
        students = Label(self.tk, padx=20, text="Student operations:", bg="#a1dbcd", font=self.customFont)
        button1 = Button(self.tk, padx=100, text="add student", command=self.__addStudent, font="bold")
        button2 = Button(self.tk, padx=83, text="remove student", font="bold", command=self.__removeStudent)
        button3 = Button(self.tk, padx=86.3, text="update student", font="bold", command=self.__updateStudent)
        button4 = Button(self.tk, padx=99, text="list students", font="bold", command=self.__listStudents)
        button5 = Button(self.tk, padx=41, text="search students by name", font="bold", command=self.__searchStudentsByName)
        button6 = Button(self.tk, padx=57, text="search students by id", font="bold", command=self.__searchStudentsById)
        ###############################################################
        disciplines = Label(self.tk, padx=20, text="Discipline operations:", bg="#a1dbcd", font=self.customFont)
        button7 = Button(self.tk, padx=100, text="add discipline", font="bold", command=self.__addDiscipline)
        button8 = Button(self.tk, padx=84, text="remove discipline", font="bold", command=self.__removeDiscipline)
        button9 = Button(self.tk, padx=86.3, text="update discipline", font="bold", command=self.__updateDiscipline)
        button10 = Button(self.tk, padx=99, text="list disciplines", font="bold", command=self.__listDisciplines)
        button11 = Button(self.tk, padx=41, text="search disciplines by name", font="bold", command=self.__searchDisciplinesByName)
        button12 = Button(self.tk, padx=57, text="search disciplines by id", font="bold", command=self.__searchDisciplinesById)
        ###############################################################
        grades = Label(self.tk, padx=20, text="Grade operations:", bg="#a1dbcd", font=self.customFont)
        button13 = Button(self.tk, padx=49.5, text="enroll student at discipline", font="bold", command=self.__enrollStudent)
        button14 = Button(self.tk, padx=57.4, text="add grades to a student", font="bold", command=self.__addGrade)
        button15 = Button(self.tk, padx=117, text="list grades", font="bold", command=self.__listGrades)
        ###############################################################
        statistics = Label(self.tk, padx=20, text="Statistics:", bg="#a1dbcd", font=self.customFont)
        button16 = Button(self.tk, padx=74, text="show students alphabetically", font="bold", command=self.__statistic1)
        button17 = Button(self.tk, text="show students in descending order(by grade)", font="bold", command=self.__statistic2)
        button18 = Button(self.tk, padx=50, text="show students who are failing", font="bold", command=self.__statistic3)
        button19 = Button(self.tk, padx=96, text="aggregate average", font="bold", command=self.__statistic4)
        button20 = Button(self.tk, padx=50, text="general average at all disciplines", font="bold", command=self.__statistic5)
        ###############################################################
        undo_redo = Label(self.tk, padx=20, text="Undo/Redo operation:", bg="#a1dbcd", font=self.customFont)
        button21 = Button(self.tk, padx=80, text="Undo last operation", font="bold", command=self.__undo)
        button22 = Button(self.tk, padx=80, text="Redo last operation", font="bold", command=self.__redo)

        photo = PhotoImage(file="k.png")
        w = Label(self.tk, image=photo)
        w.grid(columnspan=3)
        
        '''
        one
        '''
        students.grid(row=1)
        button1.grid(row=2)
        button2.grid(row=3)
        button3.grid(row=4)
        button4.grid(row=5)
        button5.grid(row=6)
        button6.grid(row=7)
        
        '''
        two
        '''
        disciplines.grid(row=1, column=1)
        button7.grid(row=2, column=1)
        button8.grid(row=3, column=1)
        button9.grid(row=4, column=1)
        button10.grid(row=5, column=1)
        button11.grid(row=6, column=1)
        button12.grid(row=7, column=1)
        
        '''
        three
        '''
        grades.grid(row=1, column=2)
        button13.grid(row=2, column=2)
        button14.grid(row=3, column=2)
        button15.grid(row=4, column=2)
        
        '''
        statistics
        '''
        Label(bg="#a1dbcd").grid(row=8)
        statistics.grid(row=9, columnspan=3)
        button16.grid(row=10)
        button17.grid(row=11)
        button18.grid(row=10, column=1)
        button19.grid(row=11, column=1)
        button20.grid(row=10, column=2)
        
        '''
        undo/redo
        '''
        
        Label(bg="#a1dbcd").grid(row=12)
        undo_redo.grid(row=13, columnspan=3)
        button21.grid(row=14, column=1)
        button22.grid(row=15, column=1)
   
        
        self.tk.mainloop()
        
        
    @staticmethod   
    def presetTk(obj, title="NoName"):
        obj.geometry('{}x{}'.format(300, 300))
        obj.title(title)
        obj.configure(background="#a1dbcd")
        
        
    def __listStudents(self):
        txt = "ID".ljust(10) + "Name".ljust(15) + "\n" 
        l = self.studentController.getStudents()
        for i in l:
            txt += str(i.getId).ljust(10) + i.name.ljust(15) + "\n"
        messagebox.showinfo("List students", txt)
        
    def __listDisciplines(self):
        txt = "ID".ljust(10) + "Name".ljust(15) + "\n" 
        l = self.disciplineController.getDisciplines()
        for i in l:
            txt += str(i.getId).ljust(10) + i.name.ljust(15) + "\n"
        messagebox.showinfo("List students", txt)

    '''
    Statistics
    '''
    def __statistic1(self):
        l = self.gradeController.sortAsc()
        messagebox.showinfo("List students", self.gradeController.listToString(l))
        
    def __statistic2(self):
        l = self.gradeController.sortDescGrades()
        messagebox.showinfo("List students", self.gradeController.listToString(l))
        
    def __statistic3(self):
        l = self.gradeController.failingDisciplines()
        messagebox.showinfo("List students", self.gradeController.listToString(l))
        
    def __statistic4(self):
        l = self.gradeController.aggregateAverage()
        messagebox.showinfo("List students", self.gradeController.listToString2(l))
        
    def __statistic5(self):
        l = self.gradeController.aggregateAverageForDisciplines()
        messagebox.showinfo("List students", self.gradeController.listToString3(l))
        
    def __listGrades(self):
        txt = "DisciplineId".ljust(15) + "StudentId".ljust(15) + "Grades".ljust(15) + "\n" 
        l = self.gradeController.getGrades()
        for i in l:
            txt += str(i.disciplineId).ljust(15) + str(i.studentId).ljust(20) + str(i.grade).ljust(15) + "\n"
        messagebox.showinfo("List grades", txt) 
    
    def __addStudent(self):
        self.newtk = Tk()
        AllInOne.presetTk(self.newtk, "Add student")
        
        
        label1 = Label(self.newtk, text="Student id:", bg="#a1dbcd", font="bold")
        label1.place(relx=0.5, rely=0.04, anchor=CENTER)
        
        self.id = Entry(self.newtk, {}, width=30)
        self.id.place(relx=0.5, rely=0.12, anchor=CENTER)
        
        label2 = Label(self.newtk, text="Student name:", bg="#a1dbcd", font="bold")
        label2.place(relx=0.5, rely=0.2, anchor=CENTER)
        
        self.name = Entry(self.newtk, {}, width=30)
        self.name.place(relx=0.5, rely=0.28, anchor=CENTER)
        
        button = Button(self.newtk, padx=10, text="Store", font=("", 10, "bold"), command=self.__storePressed)
        button.place(relx=0.5, rely=0.4, anchor=CENTER)
        self.newtk.mainloop()   
     
    def __removeStudent(self):
        self.a = Tk()
        AllInOne.presetTk(self.a, "Remove student")
        
        label = Label(self.a, text="Enter id to remove:", bg="#a1dbcd", font="bold")
        label.place(relx=0.5, rely=0.04, anchor=CENTER)
        
        self.id = Entry(self.a, {}, width=30)
        self.id.place(relx=0.5, rely=0.12, anchor=CENTER)
        
        button = Button(self.a, padx=10, text="Remove", font=("", 10, "bold"), command=self.__storePressed2)
        button.place(relx=0.5, rely=0.22, anchor=CENTER)

        self.a.mainloop()       
        
    def __updateStudent(self):
        self.b = Tk()
        AllInOne.presetTk(self.b, "Update student")
        
        label1 = Label(self.b, text="Student id:", bg="#a1dbcd", font="bold")
        label1.place(relx=0.5, rely=0.04, anchor=CENTER)
        
        self.id = Entry(self.b, {}, width=30)
        self.id.place(relx=0.5, rely=0.12, anchor=CENTER)
        
        label2 = Label(self.b, text="New student name:", bg="#a1dbcd", font="bold")
        label2.place(relx=0.5, rely=0.2, anchor=CENTER)
        
        self.name = Entry(self.b, {}, width=30)
        self.name.place(relx=0.5, rely=0.28, anchor=CENTER)
        
        button = Button(self.b, padx=10, text="Store", font=("", 10, "bold"), command=self.__storePressed3)
        button.place(relx=0.5, rely=0.4, anchor=CENTER)
        self.b.mainloop()
        
        
    def __searchStudentsByName(self):
        self.c = Tk()
        AllInOne.presetTk(self.c, "Search students by name")
        
        label = Label(self.c, text="Enter student's name to search:", bg="#a1dbcd", font="bold")
        label.place(relx=0.5, rely=0.04, anchor=CENTER)
        
        self.name = Entry(self.c, {}, width=30)
        self.name.place(relx=0.5, rely=0.12, anchor=CENTER)
        
        button = Button(self.c, padx=10, text="List students", font=("", 10, "bold"), command=self.__storePressed4)
        button.place(relx=0.5, rely=0.22, anchor=CENTER)
        
        self.c.mainloop()    
    
        
    def __searchStudentsById(self):
        self.d = Tk()
        AllInOne.presetTk(self.d, "Search students by id")
        
        label = Label(self.d, text="Enter students name to search:", bg="#a1dbcd", font="bold")
        label.place(relx=0.5, rely=0.04, anchor=CENTER)
        
        self.ident = Entry(self.d, {}, width=30)
        self.ident.place(relx=0.5, rely=0.12, anchor=CENTER)
        
        button = Button(self.d, padx=10, text="List students", font=("", 10, "bold"), command=self.__storePressed5)
        button.place(relx=0.5, rely=0.22, anchor=CENTER)
        
        
        self.d.mainloop()
        
        
    def __addDiscipline(self):    
        self.e = Tk()
        AllInOne.presetTk(self.e, "Add discipline")
        
        label1 = Label(self.e, text="Discipline id:", bg="#a1dbcd", font="bold")
        label1.place(relx=0.5, rely=0.04, anchor=CENTER)
        
        self.id = Entry(self.e, {}, width=30)
        self.id.place(relx=0.5, rely=0.12, anchor=CENTER)
        
        label2 = Label(self.e, text="Discipline name:", bg="#a1dbcd", font="bold")
        label2.place(relx=0.5, rely=0.2, anchor=CENTER)
        
        self.name = Entry(self.e, {}, width=30)
        self.name.place(relx=0.5, rely=0.28, anchor=CENTER)
        
        button = Button(self.e, padx=10, text="Store", font=("", 10, "bold"), command=self.__storePressed6)
        button.place(relx=0.5, rely=0.4, anchor=CENTER)
        self.e.mainloop() 
    
    def __removeDiscipline(self):
        self.f = Tk()
        AllInOne.presetTk(self.f, "Remove discipline")
        
        label = Label(self.f, text="Enter id to remove:", bg="#a1dbcd", font="bold")
        label.place(relx=0.5, rely=0.04, anchor=CENTER)
        
        self.id = Entry(self.f, {}, width=30)
        self.id.place(relx=0.5, rely=0.12, anchor=CENTER)
        
        button = Button(self.f, padx=10, text="Remove", font=("", 10, "bold"), command=self.__storePressed7)
        button.place(relx=0.5, rely=0.22, anchor=CENTER)

        self.f.mainloop()       
        
    def __updateDiscipline(self):
        self.g = Tk()
        AllInOne.presetTk(self.g, "Update discipline")
        
        label1 = Label(self.g, text="Discipline id:", bg="#a1dbcd", font="bold")
        label1.place(relx=0.5, rely=0.04, anchor=CENTER)
        
        self.id = Entry(self.g, {}, width=30)
        self.id.place(relx=0.5, rely=0.12, anchor=CENTER)
        
        label2 = Label(self.g, text="New discipline name:", bg="#a1dbcd", font="bold")
        label2.place(relx=0.5, rely=0.2, anchor=CENTER)
        
        self.name = Entry(self.g, {}, width=30)
        self.name.place(relx=0.5, rely=0.28, anchor=CENTER)
        
        button = Button(self.g, padx=10, text="Store", font=("", 10, "bold"), command=self.__storePressed8)
        button.place(relx=0.5, rely=0.4, anchor=CENTER)
        self.g.mainloop()   
        

    def __searchDisciplinesByName(self):
        self.h = Tk()
        AllInOne.presetTk(self.h, "Search disciplines by name")
        
        label = Label(self.h, text="Enter discipline's name to search:", bg="#a1dbcd", font="bold")
        label.place(relx=0.5, rely=0.04, anchor=CENTER)
        
        self.name = Entry(self.h, {}, width=30)
        self.name.place(relx=0.5, rely=0.12, anchor=CENTER)
        
        button = Button(self.h, padx=10, text="List disciplines", font=("", 10, "bold"), command=self.__storePressed9)
        button.place(relx=0.5, rely=0.22, anchor=CENTER)
        
        self.h.mainloop()   
        
    def __searchDisciplinesById(self):
        self.i = Tk()
        AllInOne.presetTk(self.i, "Search disciplines by id")
        
        label = Label(self.i, text="Enter discipline name to search:", bg="#a1dbcd", font="bold")
        label.place(relx=0.5, rely=0.04, anchor=CENTER)
        
        self.ident = Entry(self.i, {}, width=30)
        self.ident.place(relx=0.5, rely=0.12, anchor=CENTER)
        
        button = Button(self.i, padx=10, text="List disciplines", font=("", 10, "bold"), command=self.__storePressed10)
        button.place(relx=0.5, rely=0.22, anchor=CENTER)
        
        self.i.mainloop()
        
    def __enrollStudent(self):
        self.j = Tk()
        AllInOne.presetTk(self.j, "Enroll student")
        
        
        label1 = Label(self.j, text="Discipline id:", bg="#a1dbcd", font="bold")
        label1.place(relx=0.5, rely=0.04, anchor=CENTER)
        
        self.disciplineId = Entry(self.j, {}, width=30)
        self.disciplineId.place(relx=0.5, rely=0.12, anchor=CENTER)
        
        label2 = Label(self.j, text="Student id:", bg="#a1dbcd", font="bold")
        label2.place(relx=0.5, rely=0.2, anchor=CENTER)
        
        self.studentId = Entry(self.j, {}, width=30)
        self.studentId.place(relx=0.5, rely=0.28, anchor=CENTER)
        
       

        button = Button(self.j, padx=10, text="Enroll", font=("", 10, "bold"), command=self.__storePressed11)
        button.place(relx=0.5, rely=0.39, anchor=CENTER)
        self.j.mainloop() 
        
    def __addGrade(self):
        self.k = Tk()
        AllInOne.presetTk(self.k, "Add grades")
        
        
        label1 = Label(self.k, text="Discipline id:", bg="#a1dbcd", font="bold")
        label1.place(relx=0.5, rely=0.04, anchor=CENTER)
        
        self.disciplineId = Entry(self.k, {}, width=30)
        self.disciplineId.place(relx=0.5, rely=0.12, anchor=CENTER)
        
        label2 = Label(self.k, text="Student id:", bg="#a1dbcd", font="bold")
        label2.place(relx=0.5, rely=0.2, anchor=CENTER)
        
        self.studentId = Entry(self.k, {}, width=30)
        self.studentId.place(relx=0.5, rely=0.28, anchor=CENTER)
        
        label3 = Label(self.k, text="Grade value:", bg="#a1dbcd", font="bold")
        label3.place(relx=0.5, rely=0.36, anchor=CENTER)
        
        self.grade = Entry(self.k, {}, width=30)
        self.grade.place(relx=0.5, rely=0.44, anchor=CENTER)
        
        button = Button(self.k, padx=10, text="Enroll", font=("", 10, "bold"), command=self.__storePressed12)
        button.place(relx=0.5, rely=0.56, anchor=CENTER)
        self.k.mainloop() 
    
    
    def __storePressed(self):
        try:
            self.undoController.newOperation()
            self.studentController.addStudent(self.id.get(), self.name.get())
            messagebox.showinfo("Stored", "Student %s saved.." % self.name.get())
            print
            self.newtk.destroy()
        except Exception as e:
            self.undoController.undo()
            messagebox.showinfo("Error", "Error saving student - " + str(e))
            
    def __storePressed2(self):
        try:
            self.undoController.newOperation()
            self.studentController.removeStudent(self.id.get())
            messagebox.showinfo("Removed", "Student with id %s removed.." % self.id.get())
            print
            self.a.destroy()
        except Exception as e:
            self.undoController.undo()
            messagebox.showinfo("Error", "Error removing student - " + str(e))
    
    def __storePressed3(self):
        try:
            self.undoController.newOperation()
            self.studentController.updateStudent(self.id.get(), self.name.get())
            messagebox.showinfo("Updated", "Student %s updated.." % self.name.get())
            print
            self.b.destroy()
        except Exception as e:
            self.undoController.undo()
            messagebox.showinfo("Error", "Error updating student - " + str(e))
            
    def __storePressed4(self):
        l = self.studentController.searchForName(self.name.get())
        txt = ""
        for i in l:
            txt += i + "\n"
        self.c.destroy()
        messagebox.showinfo("List students", txt)
    
    def __storePressed5(self):
        l = self.studentController.searchForId(self.ident.get())
        txt = ""
        for i in l:
            txt += i + "\n"
        self.d.destroy()
        messagebox.showinfo("List students", txt)
        
    def __storePressed6(self):
        try:
            self.undoController.newOperation() 
            self.disciplineController.addDiscipline(self.id.get(), self.name.get())
            messagebox.showinfo("Stored", "Discipline %s saved.." % self.name.get())
            print
            self.e.destroy()
        except Exception as e:
            self.undoController.undo()
            messagebox.showinfo("Error", "Error saving discipline - " + str(e))
            
    def __storePressed7(self):
        try:
            self.undoController.newOperation() 
            self.disciplineController.removeDiscipline(self.id.get())
            messagebox.showinfo("Removed", "Discipline with id %s removed.." % self.id.get())
            print
            self.f.destroy()
        except Exception as e:
            self.undoController.undo()
            messagebox.showinfo("Error", "Error removing discipline - " + str(e))
    
    def __storePressed8(self):
        try:
            self.undoController.newOperation() 
            self.disciplineController.updateDiscipline(self.id.get(), self.name.get())
            messagebox.showinfo("Updated", "Discipline %s updated.." % self.name.get())
            print
            self.g.destroy()
        except Exception as e:
            self.undoController.undo()
            messagebox.showinfo("Error", "Error updating student - " + str(e))
            
    def __storePressed9(self):
        l = self.disciplineController.searchForName(self.name.get())
        txt = ""
        for i in l:
            txt += i + "\n"
        self.h.destroy()
        messagebox.showinfo("List disciplines", txt)

    def __storePressed10(self):
        l = self.disciplineController.searchForId(self.ident.get())
        txt = ""
        for i in l:
            txt += i + "\n"
        self.i.destroy()
        messagebox.showinfo("List disciplines", txt)
        
    def __storePressed11(self):
        try:
            self.undoController.newOperation() 
            self.gradeController.enrollStudent(self.disciplineId.get(), self.studentId.get(), [0])
            messagebox.showinfo("Enrolled", "Student with id %s enrolled.." % self.studentId.get())
            print
            self.j.destroy()
        except Exception as e:
            self.undoController.undo()
            messagebox.showinfo("Error", "Error enrolling student - " + str(e))
            
    def __storePressed12(self):
        try:
            self.undoController.newOperation() 
            self.gradeController.addGrade(self.disciplineId.get(), self.studentId.get(), [self.grade.get()])
            messagebox.showinfo("Grade added", "Student with id %s got a grade.." % self.studentId.get())
            print
            self.k.destroy()
        except Exception as e:
            self.undoController.undo()
            messagebox.showinfo("Error", "Error enrolling student - " + str(e))
            
            
    def __undo(self):
        try:
            undoMade = self.undoController.undo()
            if undoMade:
                messagebox.showinfo("Undo", "Undo done...")
            else:
                messagebox.showinfo("Undo", "No undo to perform...")
        except IndexError:
            messagebox.showinfo("Undo", "No undo to perform...")
        
    def __redo(self):
        try:
            redoMade = self.undoController.redo()
            if redoMade:
                messagebox.showinfo("Redo", "Redo done...")
            else:
                messagebox.showinfo("Redo", "No redo to perform...")
        except IndexError:
            messagebox.showinfo("Redo", "No redo to perform...")
            