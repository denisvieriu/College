package ro.ubb.pr102.web.dto;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class PizzaDto extends BaseDto {

    private String name;
    private String description;
    private float price;
    private Set<Long> ingredients;
}
