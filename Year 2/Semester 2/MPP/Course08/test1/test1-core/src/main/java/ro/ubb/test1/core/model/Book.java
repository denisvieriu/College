package ro.ubb.test1.core.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Book implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "isbn")
    private String isbn;
    private String title;
    @ManyToOne
    private Author author;
}
