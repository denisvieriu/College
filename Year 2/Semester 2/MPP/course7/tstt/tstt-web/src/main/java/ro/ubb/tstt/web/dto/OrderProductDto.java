package ro.ubb.tstt.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class OrderProductDto {
    private Long orderId;
    private Long productId;
    private Integer quantity;
}
