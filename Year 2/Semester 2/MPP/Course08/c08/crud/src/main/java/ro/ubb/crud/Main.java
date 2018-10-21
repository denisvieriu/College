package ro.ubb.crud;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.ubb.crud.model.Person;
import ro.ubb.crud.repository.PersonRepository;

/**
 * Created by radu.
 */
public class Main {
    public static void main(String[] args) {
        Person person = Person.builder().name("Alex").build();


        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("ro.ubb.crud.config");

        PersonRepository personRepository = context.getBean(PersonRepository.class);

//        personRepository.save(person);

//        personRepository.update(new Long(2));

//        personRepository.findById(2L);

//        personRepository.deleteById(3L);

        Person person2 = Person.builder().name("Name").build();

        personRepository.merge(2L, person2);

        System.out.println("hello");
    }
}
