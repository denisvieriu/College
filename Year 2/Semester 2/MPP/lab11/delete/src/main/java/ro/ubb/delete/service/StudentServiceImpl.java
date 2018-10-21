package ro.ubb.delete.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.delete.model.Student;
import ro.ubb.delete.repository.StudentRepository;

import java.util.List;

/**
 * Created by radu.
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();

    }

    @Override
    @Transactional
    public void deleteById(Long studentId) {
        Student student = studentRepository.findOne(studentId);

        student.getDisciplines().stream()
                .forEach(d -> d.getStudentDisciplines()
                        .removeIf(sd -> sd.getStudent().equals(student)));
        student.getStudentDisciplines().clear();

        studentRepository.delete(studentId);
    }
}
