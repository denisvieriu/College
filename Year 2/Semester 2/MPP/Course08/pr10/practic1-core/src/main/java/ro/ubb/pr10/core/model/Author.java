package ro.ubb.pr10.core.model;

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
public class Author extends Person {

    private String contact;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author", fetch = FetchType.LAZY)
    private Set<Book> books = new HashSet<>();
}
