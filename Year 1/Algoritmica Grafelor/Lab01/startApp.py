'''
Created on Mar 14, 2017

@author: Denis
'''

from domain.Graph import DoubleDictGraph, Edge_property
from ui.UI import UI

edgePr = Edge_property()
my_graph = DoubleDictGraph(
    r"C:\Users\Denis\Desktop\GitHub\College\Algoritmica Grafelor\Lab01\graph1k.txt", edgePr)
ui = UI(my_graph)
ui.mainMenu()