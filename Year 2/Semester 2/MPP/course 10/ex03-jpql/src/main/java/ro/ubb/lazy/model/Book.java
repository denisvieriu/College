package ro.ubb.lazy.model;

import lombok.*;

import javax.persistence.*;

/**
 * Created by radu.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"author", "publisher"}, callSuper = true)
@ToString(exclude = {"author", "publisher"})
@Builder
public class Book extends BaseEntity<Long>{


    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

    @OneToOne(fetch = FetchType.LAZY)
    private Publisher publisher;
}
