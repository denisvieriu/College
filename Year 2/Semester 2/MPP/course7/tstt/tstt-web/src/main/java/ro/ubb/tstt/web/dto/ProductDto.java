package ro.ubb.tstt.web.dto;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductDto extends BaseDto{
    private String name;
    private int price;
    private int stock;
}
