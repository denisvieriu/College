package ro.ubb.crud.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by radu.
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
