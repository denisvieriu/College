package ro.ubb.inh.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by radu.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int price;

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }
}
