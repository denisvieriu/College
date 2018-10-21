package ro.ubb.lazy.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by radu.
 */

@NamedEntityGraphs({
        @NamedEntityGraph(name = "authorWithBooks",
                attributeNodes = @NamedAttributeNode(value = "books")),
        @NamedEntityGraph(name = "authorWithBooksAndPublisher",
                attributeNodes = @NamedAttributeNode(value = "books", subgraph = "bookWithPublisher"),
                subgraphs = @NamedSubgraph(name = "bookWithPublisher",
                        attributeNodes = @NamedAttributeNode(value = "publisher")))
})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class Author extends BaseEntity<Long> {

    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author", fetch = FetchType.LAZY)
    private Set<Book> books = new HashSet<>();


}
