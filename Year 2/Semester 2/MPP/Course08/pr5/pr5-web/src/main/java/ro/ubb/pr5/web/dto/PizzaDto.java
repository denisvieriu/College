package ro.ubb.pr5.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PizzaDto extends BaseDto {
    private String name;
    private String description;
    private float price;
}
