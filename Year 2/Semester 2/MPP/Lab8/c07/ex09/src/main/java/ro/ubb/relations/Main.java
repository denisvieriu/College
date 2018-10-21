package ro.ubb.relations;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.ubb.relations.model.Author;
import ro.ubb.relations.model.Publisher;
import ro.ubb.relations.repository.PublisherRepository;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by radu.
 * m-2-1 unidir fk
 */
public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("ro.ubb.relations.config");

        PublisherRepository publisherRepository = context.getBean(PublisherRepository.class);
//        BookRepository bookRepository = context.getBean(BookRepository.class);

        Publisher publisher1 = Publisher.builder().name("manning").build();
        Publisher publisher2 = Publisher.builder().name("manning").build();

        Author author1 = Author.builder().name("author1").build();
        Author author2 = Author.builder().name("author2").build();

        publisher1.setAuthors(new HashSet<>(Arrays.asList(author1)));
        publisher2.setAuthors(new HashSet<>(Arrays.asList(author1, author2)));

        author1.setPublishers(new HashSet<>(Arrays.asList(publisher1, publisher2)));
        author2.setPublishers(new HashSet<>(Arrays.asList(publisher2)));


//        bookRepository.save(book);


        publisherRepository.save(Arrays.asList(publisher1, publisher2));

        System.out.println("hello");
    }
}
