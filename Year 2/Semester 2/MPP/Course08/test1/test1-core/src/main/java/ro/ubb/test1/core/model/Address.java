package ro.ubb.test1.core.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter

public class Address {
    @Id
    @GeneratedValue
    private Long id;
    private String city;

    @ManyToOne
    private Person person;
}
