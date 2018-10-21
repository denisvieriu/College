package ro.ubb.catalog.core.model;

import lombok.*;

import javax.persistence.Entity;

/**
 * Created by radu.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Discipline extends BaseEntity<Long> {
    private String name;
    private String teacher;
    private int credits;

    @Override
    public String toString() {
        return "Discipline{" +
                "name='" + name + '\'' +
                ", teacher='" + teacher + '\'' +
                ", credits=" + credits +
                "} " + super.toString();
    }
}
