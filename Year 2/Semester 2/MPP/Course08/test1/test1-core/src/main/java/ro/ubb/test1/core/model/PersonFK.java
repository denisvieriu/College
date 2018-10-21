package ro.ubb.test1.core.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "PersonFK")
@DiscriminatorColumn(name = "PERSON_FK_TYPE")
@Getter
@Setter
public class PersonFK {

    @Column
    private String name;

    @Id
    private int id;
}
