package ro.ubb.relations.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by radu.
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Adress implements Serializable {
//    @Id
//    @GeneratedValue
//    private Long id;

    private String city;

    private String street;
}
