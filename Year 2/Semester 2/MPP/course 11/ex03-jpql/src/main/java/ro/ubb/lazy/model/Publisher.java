package ro.ubb.lazy.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by radu.
 */

@Entity
@Table(name = "publisher")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class Publisher extends BaseEntity<Long> {


    private String name;

}
