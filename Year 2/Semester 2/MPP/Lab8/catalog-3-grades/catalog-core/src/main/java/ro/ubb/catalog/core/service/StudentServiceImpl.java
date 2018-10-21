package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.BaseEntity;
import ro.ubb.catalog.core.model.Discipline;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.core.repository.DisciplineRepository;
import ro.ubb.catalog.core.repository.StudentRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentServiceImpl implements StudentService {
    private static final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DisciplineRepository disciplineRepository;


    @Override
    public Optional<Student> findStudent(Long studentId) {
        log.trace("findStudent: studentId={}", studentId);

        Optional<Student> studentOptional = studentRepository.findById(studentId);

        log.trace("findStudent: studentOptional={}", studentOptional);

        return studentOptional;
    }

    @Override
    public List<Student> findAll() {
        log.trace("findAll --- method entered");

        List<Student> students = studentRepository.findAll();

        log.trace("findAll: students={}", students);

        return students;
    }

    @Override
    @Transactional
    public Student updateStudent(Long studentId, String serialNumber, String name, Integer groupNumber,
                                 Set<Long> disciplines) {
        log.trace("updateStudent: serialNumber={}, name={}, groupNumber={}, disciplines={}", serialNumber, name, groupNumber, disciplines);

        Optional<Student> student = studentRepository.findById(studentId);

        student.ifPresent(s -> {
            s.setSerialNumber(serialNumber);
            s.setName(name);
            s.setGroupNumber(groupNumber);

            s.getDisciplines().stream()
                    .map(BaseEntity::getId)
                    .forEach(disciplines::remove);
            List<Discipline> disciplineList = disciplineRepository.findAllById(disciplines);
            disciplineList.forEach(s::addDiscipline);
        });

        log.trace("updateStudent: student={}", student.get());

        return student.orElse(null);
    }

    @Override
    public Student createStudent(String serialNumber, String name, Integer groupNumber) {
        log.trace("createStudent: serialNumber={}, name={}, groupNumber={}",
                serialNumber, name, groupNumber);

        Student student = Student.builder()
                .serialNumber(serialNumber)
                .name(name)
                .groupNumber(groupNumber)
                .build();
        student = studentRepository.save(student);

        log.trace("createStudent: student={}", student);

        return student;
    }

    @Override
    public void deleteStudent(Long studentId) {
        log.trace("deleteStudent: studentId={}", studentId);

        studentRepository.deleteById(studentId);

        log.trace("deleteStudent - method end");
    }

    @Override
    @Transactional
    public Optional<Student> updateStudentGrades(Long studentId, Map<Long, Integer> grades) {
        log.trace("updateStudentGrades: studentId={}, grades={}", studentId, grades);

        Optional<Student> studentOptional = studentRepository.findById(studentId);
        studentOptional.ifPresent(student ->
                student.getStudentDisciplines()
                        .forEach(sd ->
                                sd.setGrade(grades.get(sd.getDiscipline().getId())))
        );

        log.trace("updateStudentGrades: studentOptional={}", studentOptional);

        return studentOptional;
    }

}
