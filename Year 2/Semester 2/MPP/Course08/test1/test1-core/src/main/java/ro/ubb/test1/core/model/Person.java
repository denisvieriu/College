package ro.ubb.test1.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Person {
    @Id
    @GeneratedValue
    private Long id;
    private String name;


    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "person")
    private List<Address> addresses;
}
