package ro.ubb.catalog.core.service;

import ro.ubb.catalog.core.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Student> getAllStudents();

    Student createStudent(String name, int grade);

    Optional<Student> updateStudent(Long studentId, String name, int grade);

    void deleteStudent(Long id);
}
