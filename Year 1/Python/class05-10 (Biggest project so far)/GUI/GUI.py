'''
Created on Nov 21, 2016

@author: Denis
'''
from tkinter import *
from tkinter import messagebox


def commandUI():
    
    print
    
root = Tk()  # tkinker class(blink windows)

topFrame = Frame(root)  # invisible container
topFrame.pack()
bottomFrame = Frame(root)
bottomFrame.pack()

two = Label(topFrame, text="Students operations:", bg="green", fg="black")  # background - bf, foregroung - fg
two.pack(fill=X)  # if we make the window bigger it will fill
button1 = Button(topFrame, text="Add student to the list", fg="black")  # fg-color
one = Label(topFrame, text="".ljust(100))
button2 = Button(topFrame, text="Button 2", fg="blue")

button4 = Button(bottomFrame, text="Add student to the list", fg="purple")
two = Label(bottomFrame, text="".ljust(100))
button5 = Button(bottomFrame, text="Button 5", fg="black")


button1.pack(side=LEFT, fill=X)
one.pack(side=LEFT)
button2.pack(side=LEFT)

button4.pack(side=LEFT)
two.pack(side=LEFT)
button5.pack(side=LEFT)
root.mainloop()
