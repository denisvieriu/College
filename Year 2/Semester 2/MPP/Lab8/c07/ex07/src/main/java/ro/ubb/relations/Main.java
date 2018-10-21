package ro.ubb.relations;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.ubb.relations.model.Book;
import ro.ubb.relations.model.Publisher;
import ro.ubb.relations.repository.BookRepository;
import ro.ubb.relations.repository.PublisherRepository;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by radu.
 */
public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("ro.ubb.relations.config");

        PublisherRepository publisherRepository=context.getBean(PublisherRepository.class);
       // BookRepository bookRepository=context.getBean(BookRepository.class);



        Publisher publisher=Publisher.builder().name("manning").build();
        Book book = Book.builder().title("baltagul").author("sadoveanu").
                publisher(publisher).build();

        Book book1 = Book.builder().title("Moara cu noroc").author("slavici").
                publisher(publisher).build();

        publisher.setBooks(new HashSet<>(Arrays.asList(book, book1)));

        publisherRepository.save(publisher);
        //bookRepository.save(book);


        System.out.println("hello");
    }
}
