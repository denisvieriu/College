package ro.ubb.pr10.core.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "book")
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraphs(@NamedEntityGraph(name="booksWithAuthors", attributeNodes = @NamedAttributeNode(value = "author")))
@EqualsAndHashCode(callSuper = true, exclude = "author")
@ToString(callSuper = true, exclude = "author")
@Data
public class Book extends BaseEntity<Long> {

    private String title;

    private int year;

    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;


}
