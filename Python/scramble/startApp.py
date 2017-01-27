'''
Created on Jan 26, 2017

@author: Denis
'''
from repository.FileRepository import FileRepository
from controller.RepoController import RepoController
from ui.UI import UI
repo = FileRepository("input.txt")
controller = RepoController(repo)
ui = UI(controller)

ui.mainMenu()