package ro.ubb.inh;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.ubb.inh.model.Battery;
import ro.ubb.inh.model.Product;
import ro.ubb.inh.repository.ProductRepository;

import java.util.List;

/**
 * Created by radu.
 */


public class Main {


    public static void main(String args[]) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("ro.ubb.inh.config");

        ProductRepository productRepository = context.getBean(ProductRepository.class);

        Product product = new Product("p1", 1);

        Battery battery = Battery.builder().name("b1").price(2).rechargeable(true).build();

        productRepository.save(product);
        productRepository.save(battery);


        List<Product> products=productRepository.findAll();
        products.forEach(System.out::println);

    }


}
