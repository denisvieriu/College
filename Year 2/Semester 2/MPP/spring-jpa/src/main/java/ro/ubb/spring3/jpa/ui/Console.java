package ro.ubb.spring3.jpa.ui;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ubb.spring3.jpa.domain.Student;
import ro.ubb.spring3.jpa.service.StudentService;

/**
 * Created by radu.
 */
@Component
public class Console {
    @Autowired
    private StudentService studentService;

    public void runConsole() {
        //add student
        studentService.addStudent(new Student("test", 1));

        //get all students
        studentService.getAll().forEach(System.out::println);

        studentService.getAll().stream().findFirst()
                .ifPresent(student -> {
                    Long id = student.getId();

                    //update student
                    Student s = new Student("testupdate", 1);
                    s.setId(id);
                    studentService.updateStudent(s);

                    //delete Student
                    studentService.deleteStudent(id);
                });
    }
}
