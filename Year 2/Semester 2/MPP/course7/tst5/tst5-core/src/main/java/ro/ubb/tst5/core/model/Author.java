package ro.ubb.tst5.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("Author")
@NamedEntityGraphs(@NamedEntityGraph(name="authorWithBooks", attributeNodes = @NamedAttributeNode(value = "books")))
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Builder
public class Author extends BaseEntity<Long> {

    @Column(unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    private Set<Book> books = new HashSet<>();
}
