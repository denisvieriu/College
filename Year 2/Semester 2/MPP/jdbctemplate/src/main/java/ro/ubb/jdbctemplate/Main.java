package ro.ubb.jdbctemplate;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.ubb.jdbctemplate.repository.StudentRepository;

public class Main {
    public static void main(String[]args){
        System.out.println("hello world");

        AnnotationConfigApplicationContext context=
                new AnnotationConfigApplicationContext("ro.ubb.jdbctemplate.config");

        StudentRepository studentRepository=context.getBean(StudentRepository.class);

        studentRepository.findAll()
                .forEach(System.out::println);

        System.out.println("bye");
    }
}
