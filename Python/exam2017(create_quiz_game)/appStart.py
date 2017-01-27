'''
Created on Jan 27, 2017

@author: Denis
'''

from repository.FileRepository import FileRepository
from controller.RepoController import RepoController
from ui.UI import UI

repo = FileRepository("master.txt")
controller = RepoController(repo)
ui = UI(controller)
ui.mainMenu()