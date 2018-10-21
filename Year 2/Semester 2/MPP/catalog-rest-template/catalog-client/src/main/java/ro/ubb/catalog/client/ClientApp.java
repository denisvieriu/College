package ro.ubb.catalog.client;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ro.ubb.catalog.core.model.Student;
import ro.ubb.catalog.web.dto.StudentDto;
import ro.ubb.catalog.web.dto.StudentsDto;

import java.util.List;
import java.util.Map;

/**
 * Created by radu.
 */
public class ClientApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("ro.ubb.catalog.client.config");
        RestTemplate restTemplate = context.getBean(RestTemplate.class);

        StudentsDto studentsDto = restTemplate
                .getForObject("http://localhost:8080/api/students", StudentsDto.class);
        studentsDto.getStudents()
                .forEach(System.out::println);


        StudentDto student = restTemplate
                .postForObject("http://localhost:8080/api/students",
                        new StudentDto("s1", 10),
                        StudentDto.class);
        System.out.println(student);


        student.setGrade(student.getGrade() + 1);
        restTemplate
                .put("http://localhost:8080/api/students/{studentId}",
                        student, student.getId());


        restTemplate
                .delete("http://localhost:8080/api/students/{studentId}",
                        student.getId());

        System.out.println("bye ");
    }
}
