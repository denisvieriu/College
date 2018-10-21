package ro.ubb.pr10.core.model;

import lombok.*;

import javax.persistence.*;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Person_Type")
@Table(name = "person")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data

@Builder
public class Person extends BaseEntity<Long> {

    @Column(unique = true, nullable = false)
    private String ssn;

    @Column(nullable = false)
    private String name;
}

