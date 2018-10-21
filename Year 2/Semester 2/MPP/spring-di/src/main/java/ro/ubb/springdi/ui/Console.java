package ro.ubb.springdi.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ubb.springdi.service.StudentService;

/**
 * Created by radu.
 */
@Component
public class Console {

    @Autowired
    private StudentService studentService;

    public void runConsole() {
        studentService.getAllStudents()
                .forEach(System.out::println);
    }
}
