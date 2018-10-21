package ro.ubb.relations.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by radu.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = "publishers")
@ToString(exclude = "publishers")
@Builder
public class Author {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToMany
    private Set<Publisher> publishers = new HashSet<>();
}
