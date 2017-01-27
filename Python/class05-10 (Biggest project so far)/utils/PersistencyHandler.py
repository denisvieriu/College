'''
Created on Jan 6, 2017

@author: Denis
'''

from repository.Repository import Repository, GradeRepository
from repository.FileRepository import FileRepository
from repository.FileGradeRepository import FileGradeRepository
from domain.Student import Student
from domain.Grade import Grade
from domain.Discipline import Discipline
from repository.PickleRepository import PickleRepository, PickleGradeRepository
from repository.SQL_Repo import SQL_Repo, SQL_GradeRepo

class PersistencyHandler:
    @staticmethod
    def obtainPersistencySource():
        with open("./local.settings", "r") as f:
            lines = f.readlines()
            repo_type = lines[0].strip().split('=')[1]
        if repo_type == "inmemory":
            return Repository(), Repository(), GradeRepository()
        if repo_type == "file":
            students_repo_file = lines[1].strip().split('=')[1]
            discipline_repo_file = lines[2].strip().split('=')[1]
            grade_repo_file = lines[3].strip().split('=')[1]
            return FileRepository(students_repo_file, Student), FileRepository(discipline_repo_file, Discipline), FileGradeRepository(grade_repo_file, Grade)
        if repo_type == "binary":
            students_repo_file = lines[1].strip().split('=')[1]
            discipline_repo_file = lines[2].strip().split('=')[1]
            grade_repo_file = lines[3].strip().split('=')[1]
            return PickleRepository(students_repo_file), PickleRepository(discipline_repo_file), PickleGradeRepository(grade_repo_file)
        if repo_type == "sql":
            students_repo_file = lines[1].strip().split('=')[1]
            discipline_repo_file = lines[2].strip().split('=')[1]
            grade_repo_file = lines[3].strip().split('=')[1]
            return SQL_Repo(students_repo_file, Student), SQL_Repo(discipline_repo_file, Discipline), SQL_GradeRepo(grade_repo_file, Grade)
            
