'''
Created on Nov 21, 2016

@author: Denis
'''
from tkinter import *

root = Tk()

one = Label(root, text="One", bg="red", fg="white")  # background - bf, foregroung - fg
one.pack()
two = Label(root, text="Two", bg="green", fg="black")  # background - bf, foregroung - fg
two.pack(fill=X)  # if we make the window bigger it will fill
three = Label(root, text="Three", bg="blue", fg="white")  # background - bf, foregroung - fg
three.pack(side=LEFT, fill=Y)

root.mainloop()
