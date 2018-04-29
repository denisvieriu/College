'''
Created on Mar 14, 2017

@author: Denis
'''

from domain.Graph import GraphException


class UI:
    '''
    classdocs
    '''

    def __init__(self, graph):
        self._graph = graph

    @staticmethod
    def readUserCommand():
        return input("Enter the command:")

    @staticmethod
    def getVertices(graph):
        s = "The number of vertices is "
        s += str(graph.numberOfVertices())
        return s

    @staticmethod
    def edgeXY(graph):
        x = int(input("x = "))
        y = int(input("y = "))
        if graph.isEdge(x, y) == True:
            return "There is edge between {} and {}".format(x, y)
        else:
            return "There is no edge between {} and {}".format(x, y)

    @staticmethod
    def in_out_degree(graph):
        x = int(input("Enter the vertex : "))
        s = "The in-degree for node {} is {}\n".format(x, graph.inDegree(x))
        s += "The out-degree for node {} is {}".format(x, graph.outDegree(x))
        return s

    @staticmethod
    def outboundEdges(graph):
        x = int(input("x = "))
        s = graph.parseNout(x)
        return "The outbound edges for {} are:\n {}".format(x, s)

    @staticmethod
    def inboundEdges(graph):
        x = int(input("x = "))
        s = graph.parseNin(x)
        return "The inbound edge for {} are:\n {}".format(x, s)

    @staticmethod
    def costEdges(graph):
        x = int(input("Enter x : "))
        y = int(input("Enter y : "))
        return "The cost between {} and {} is {}".format(x, y, graph._edgeProperty.getCost(x, y))

    @staticmethod
    def removeEdge(graph):
        x = int(input("Enter x : "))
        y = int(input("Enter y : "))
        graph.removeEdge(x, y)
        return "Done!"

    @staticmethod
    def addEdge(graph):
        x = int(input("Enter x : "))
        y = int(input("Enter y : "))
        cost = int(input("Enter cost : "))
        graph.addEdge(x, y, cost)
        return "Done!"

    def mainMenu(self):
        commands = {
            "1": UI.getVertices, "2": UI.edgeXY, "3": UI.in_out_degree,
            "4": UI.outboundEdges, "5": UI.inboundEdges, "6": UI.costEdges,
            "8": UI.addEdge, "9": UI.removeEdge}
        menu = UI.getMenu()
        print(menu)
        while True:
            command = UI.readUserCommand()
            try:
                if command in commands:
                    print(commands[command](self._graph))
                else:
                    print("Invalid command!")
            except GraphException as msg:
                print(msg)
            except:
                print("Error!")

    @staticmethod
    def getMenu():
        s = "Available commands:\n"
        s += "\t 1 - get the number of vertices\n"
        s += "\t 2 - search if there is any edge between x and y\n"
        s += "\t 3 - get the in-degree and out-degree of a vertex\n"
        s += "\t 4 - outbound edges of a vertex\n"
        s += "\t 5 - inbound edges of a vertex\n"
        s += "\t 6 - get the cost between two vertices\n"
        s += "\t 7 - modify the cost between two vertices\n"
        s += "\t 8 - add an edge\n"
        s += "\t 9 - remove an edge\n"
        s += "\t 10 - add an vertex\n"
        s += "\t 11 - remove an vertex\n"
        return s
