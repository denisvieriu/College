package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.core.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private static final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        log.trace("getAllStudents --- method entered");

        List<Student> students = studentRepository.findAll();

        log.trace("getAllStudents: students={}", students);

        return students;
    }

    @Override
    public Student createStudent(String name, int grade) {
        log.trace("saveStudent: name={}, grade={}", name, grade);

        Student student = studentRepository.save(new Student(name, grade));

        log.trace("saveStudent: student={}", student);

        return student;
    }

    @Override
    @Transactional
    public Optional<Student> updateStudent(Long studentId, String name, int grade) {
        log.trace("updateStudent: studentId={}, name={}, grade={}", studentId, name, grade);

        Optional<Student> optionalStudent = studentRepository.findById(studentId);

        optionalStudent.ifPresent(st -> {
            st.setName(name);
            st.setGrade(grade);
        });

        log.trace("updateStudent: optionalStudent={}", optionalStudent);

        return optionalStudent;
    }

    @Override
    public void deleteStudent(Long id) {
        log.trace("deleteStudent: id={}", id);

        studentRepository.deleteById(id);

        log.trace("deleteStudent --- method finished");
    }


}
