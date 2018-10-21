package ro.ubb.for10_1.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class ProductDto extends BaseDto {
    private String name;
    private Integer stock;
    private Integer price;
}
