package ro.ubb.test1.core;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.ubb.test1.core.model.Author;
import ro.ubb.test1.core.model.Book;
import ro.ubb.test1.core.repository.AuthorRepository;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Transactional
public class main {

    static class Hello<T> {
        T t;

        public Hello(T t) {
            this.t = t;
        }

        public String toString() {
            return t.toString();
        }
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("ro.ubb.test1.core.config");
//
//        System.out.println(new Hello<String>("hi"));
//        System.out.println(new Hello("there"));

//        AuthorRepository authorRepositor = context.getBean(AuthorRepository.class);
//        List<Author> authors = authorRepositor.findAll();
//        authors.forEach(System.out::println);


        Author author = Author.builder().name("Bruce Eckel").build();

        Book book1 = Book.builder().isbn("001").title("Thinking in Java").author(author).build();
        Book book2 = Book.builder().isbn("002").title("Thinking in C++").author(author).build();

        author.setBooks(Arrays.asList(book1, book2));
        AuthorRepository authorRepository = context.getBean(AuthorRepository.class);
        authorRepository.save(author);
//
//        authorRepository.delete(author);

//        PersonFkRepository personFkRepository = context.getBean(PersonFkRepository.class);
//        ChildFkRepository childFkRepository = context.getBean(ChildFkRepository.class);
//
//        ChildFK childFK = new ChildFK();
//        childFK.setRechargable(true);
//
//        PersonFK personFK = new PersonFK();
//        personFK.setId(1);
//        personFK.setName("pers");
//
//        childFkRepository.save(childFK);
//
//        personFkRepository.save(personFK);

    }
}
