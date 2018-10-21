package ro.ubb.pr102.web.dto;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class IngredientDto extends BaseDto {

    private String name;

}
