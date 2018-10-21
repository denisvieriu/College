package ro.ubb.catalog.core.model;

import lombok.*;

import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class Student extends BaseEntity<Long> {

    @NotEmpty
    private String serialNumber;

//    @NotEmpty
    private String name;

    @Max(9)
    private Integer groupNumber;

    @Override
    public String toString() {
        return "Student{" +
                "serialNumber='" + serialNumber + '\'' +
                ", name='" + name + '\'' +
                ", groupNumber=" + groupNumber +
                "} " + super.toString();
    }
}
