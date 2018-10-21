package ro.ubb.for5_1.core.model;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.Table;


@Entity
@Table(name = "person")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class Person extends BaseEntity<Long> {

    @Column
    private String name;

    @Column(unique = true)
    private String ssn;
}
