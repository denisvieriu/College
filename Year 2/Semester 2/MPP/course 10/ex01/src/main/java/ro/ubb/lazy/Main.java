package ro.ubb.lazy;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.ubb.lazy.model.Author;
import ro.ubb.lazy.model.Publisher;
import ro.ubb.lazy.service.AuthorService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by radu.
 */
public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("ro.ubb.lazy.config");

        AuthorService authorService = context.getBean(AuthorService.class);
        List<Author> all = authorService.findAll();

        all.forEach(System.out::println);

        System.out.println(all);

        System.out.println("hello");
    }
}
