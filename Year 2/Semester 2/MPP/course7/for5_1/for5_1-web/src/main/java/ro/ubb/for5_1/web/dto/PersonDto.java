package ro.ubb.for5_1.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PersonDto extends BaseDto {
    private String ssn;
    private String name;
}
