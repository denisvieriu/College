package ro.ubb.lazy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Builder
public class Author {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author", fetch = FetchType.LAZY)
    private Set<Book> books = new HashSet<>();


}
