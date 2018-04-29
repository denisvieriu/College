'''
Created on Nov 27, 2016

@author: Denis
'''

from tkinter import *

root = Tk()

button1 = Button(root, padx=30, text="Button 1")

button2 = Button(root, text="Button 2")
button3 = Button(root, text="Buttgasgon agasga3")
button4 = Button(root, text="Button 4")
button1.grid(row=0)
button2.grid(row=0, column=1)
button3.grid(row=1)

root.mainloop()
