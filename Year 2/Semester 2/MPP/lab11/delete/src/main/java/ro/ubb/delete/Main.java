package ro.ubb.delete;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.ubb.delete.service.StudentService;

/**
 * Created by radu.
 */
public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("ro.ubb.delete.config");

        StudentService studentService = context.getBean(StudentService.class);

        studentService.findAll().forEach(System.out::println);

        System.out.println("Delete: -----------");

        studentService.deleteById(-1L);

        System.out.println("Find all ------------");

        studentService.findAll().forEach(System.out::println);

        System.out.println("hello");
    }
}
