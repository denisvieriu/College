package ro.ubb.relations.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by radu.
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = "publisher")
@ToString
@Builder
public class Book implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String author;

    @ManyToOne
    private Publisher publisher;


}
