'''
Created on Jan 7, 2017

@author: Denis
'''
from repository.Repository import Repository
import sqlite3

class SQL_Repo(Repository):
    '''
    SQLite3 Repository
    '''
    def __init__(self, fileName, objectType):
        Repository.__init__(self)  # initialising the repository with its list
        self._fileName = fileName  # the filename of the table that will be created
        self._objectType = objectType  # The object type e.g : Student, Discipline etc..
        self._conn = sqlite3.connect("DATABASE.db")  # Creating the connection to the database
        self._c = self._conn.cursor()  # Getting the cursor for the database ( like a real cursor )
        self._createTable()  # Creating the table if not created 
        self._readFromDB()  # Reading from the table ( if there's any input data to be read )
        
    def _createTable(self):
        self._c.execute('CREATE TABLE IF NOT EXISTS ' + self._fileName + '(id TEXT, name TEXT)')
     
    def _readFromDB(self):
        '''
        Reading from a database
        * - selects everything from the database, like a cursor selects something on the screen;
            to use the data we use fetchall, which returns a list of tuples;
            we use join to make every tuple from the list a string;
            the rest is the same as reading from file;
        '''
        self._c.execute('SELECT * FROM ' + self._fileName)
        lines = self._c.fetchall()
        for line in lines:
                if line != '\n':
                    line = ','.join(line)
                    obj = self._objectType.readFromLine(line)
                    self._entities[obj.getId] = obj
           
    def _writeToDB(self):
        '''
        Writing to a database
            we 'DROP' the table ( we want to overwrite in the table with every modification made to the program );
            then we create another table with the same one as previous to write the new modifications;
        '''
        self._c.execute('DROP TABLE IF EXISTS ' + self._fileName)
        self._createTable()
        for obj_id in self._entities:
            self._c.execute("INSERT INTO " + self._fileName + " (id, name) VALUES (?, ?)",
                            (self._entities[obj_id].getId, self._entities[obj_id].name))
        self._conn.commit()  # saving the changes; like writing in a notepad file and saving it at final
        

    def store(self, elem):
        Repository.store(self, elem)
        self._writeToDB()
        
    def remove(self, ident):
        Repository.remove(self, ident)
        self._writeToDB()
        # #self._c.execute('DELETE FROM %s where id = %s' %(ident))
        # #self._conn.commit()
        
    def update(self, elem):
        Repository.update(self, elem)
        self._writeToDB()
        '''
        Same as self._writeToDB()
        '''
        # # self._c.execute('UPDATE %s set name = %s WHERE ident = %s' %(self._fileName, elem.name, elem.getId))
        # # self._conn.commit()
