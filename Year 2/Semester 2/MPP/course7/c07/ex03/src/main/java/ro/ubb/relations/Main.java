package ro.ubb.relations;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.ubb.relations.model.Adress;
import ro.ubb.relations.model.Publisher;
import ro.ubb.relations.repository.PublisherRepository;

/**
 * Created by radu.
 */
public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("ro.ubb.relations.config");

        PublisherRepository publisherRepository=context.getBean(PublisherRepository.class);


        Adress adress = Adress.builder().city("Cluj").street("Republicii").build();

        Publisher publisher=Publisher.builder().name("manning").adress(adress).build();

        publisherRepository.save(publisher);


        System.out.println("hello");
    }
}
