package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.core.repository.StudentRepository;
import ro.ubb.catalog.core.validators.StudentValidator;

import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class StudentServiceImpl implements StudentService {
    private static final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private Validator validator;

    @Autowired
    private StudentValidator studentValidator;


    @Override
    public List<Student> findAll() {
        log.trace("findAll --- method entered");

        List<Student> students = studentRepository.findAll();

        log.trace("findAll: students={}", students);

        return students;
    }

    @Override
    @Transactional
    public Student updateStudent(@Valid Student student) {
        log.trace("updateStudent: student={}", student);


        if(student!=null) {
            throw new IllegalArgumentException("simulated exception in StudentService.updateStudent");
        }

        Optional<Student> st = studentRepository.findById(student.getId());

        st.ifPresent(s -> {
            s.setSerialNumber(student.getSerialNumber());
            s.setName(student.getName());
            s.setGroupNumber(student.getGroupNumber());
        });

        log.trace("updateStudent: st={}", st.get());

        return st.orElse(null);
    }

//    @Override
//    @Transactional
//    public Student updateStudent(Student student) {
//        log.trace("updateStudent: student={}", student);
//
//        BindException bindException = new BindException(student, "student");
//        studentValidator.validate(student, bindException);
//        for (FieldError fieldError : bindException.getFieldErrors()) {
//            log.trace("errors in student={} --- error={}", student, fieldError);
//        }
//        if (bindException.hasErrors()) {
//            log.trace("errors when validating student");
//            //TODO custom exception
//            throw new IllegalArgumentException("errors errors when validating student");
//        }
//
//        Optional<Student> st = studentRepository.findById(student.getId());
//
//        st.ifPresent(s -> {
//            s.setSerialNumber(student.getSerialNumber());
//            s.setName(student.getName());
//            s.setGroupNumber(student.getGroupNumber());
//        });
//
//        log.trace("updateStudent: st={}", st.get());
//
//        return st.orElse(null);
//    }

}
