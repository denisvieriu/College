package ro.ubb.lazy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Created by radu.
 */
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseEntity<ID extends Serializable> implements Serializable {
    @Id
    @GeneratedValue
    private ID id;
}
