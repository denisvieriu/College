'''
Created on Nov 19, 2016

@author: Denis
'''


class IDObject():

    def __init__(self, objectId):
        self._objectId = objectId

    @property
    def getId(self):
        return self._objectId
