package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.core.service.StudentService;
import ro.ubb.catalog.web.converter.StudentConverter;
import ro.ubb.catalog.web.dto.StudentDto;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by radu.
 */

@RestController
public class StudentController {

    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentConverter studentConverter;


    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public List<StudentDto> getStudents() {
        log.trace("getStudents");

        List<Student> students = studentService.findAll();

        log.trace("getStudents: students={}", students);

        return new ArrayList<>(studentConverter.convertModelsToDtos(students));
    }

    @RequestMapping(value = "/students/{studentId}", method = RequestMethod.PUT)
    public StudentDto updateStudent(
            @PathVariable final Long studentId,
            @RequestBody @Valid  final StudentDto studentDto) {
        log.trace("updateStudent: studentId={}, studentDtoMap={}", studentId, studentDto);

        Student updateStudent = Student.builder()
                .serialNumber(studentDto.getSerialNumber())
                .name(studentDto.getName())
                .groupNumber(studentDto.getGroupNumber())
                .build();
        updateStudent.setId(studentDto.getId());
        Student student = studentService.updateStudent(updateStudent);

        StudentDto result = studentConverter.convertModelToDto(student);

        log.trace("updateStudent: result={}", result);

        return result;
    }


}
