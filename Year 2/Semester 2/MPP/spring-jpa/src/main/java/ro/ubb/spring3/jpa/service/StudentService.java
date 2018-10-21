package ro.ubb.spring3.jpa.service;

import org.springframework.transaction.annotation.Transactional;
import ro.ubb.spring3.jpa.domain.Student;

import java.util.List;

/**
 * Created by radu.
 */
public interface StudentService {

    List<Student> getAll();

    Student findStudent(Long id);

    void addStudent(Student student);

    void updateStudent(Student student);

    void deleteStudent(Long id);
}
