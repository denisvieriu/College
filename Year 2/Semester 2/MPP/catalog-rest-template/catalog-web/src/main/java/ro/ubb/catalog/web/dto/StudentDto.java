package ro.ubb.catalog.web.dto;

import lombok.*;

/**
 * Created by radu.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentDto extends BaseDto {
    private String name;
    private int grade;

    @Override
    public String toString() {
        return "StudentDto{" +
                "name='" + name + '\'' +
                ", grade=" + grade +
                "} " + super.toString();
    }
}
