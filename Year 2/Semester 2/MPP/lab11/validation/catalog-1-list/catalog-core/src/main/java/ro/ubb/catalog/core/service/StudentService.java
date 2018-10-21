package ro.ubb.catalog.core.service;

import ro.ubb.catalog.core.model.Student;

import java.util.List;

public interface StudentService {
    List<Student> findAll();

    Student updateStudent(Student student);

}
