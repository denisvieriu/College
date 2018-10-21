package ro.ubb.for10_1.web.dto;

import lombok.*;
import ro.ubb.for10_1.core.model.Order;

import java.util.Date;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class OrderDto extends BaseDto {
    private String address;
    private String email;
    private Date date;
    private Order.State status;
    private Integer totalPrice;

    private Set<Long> products;
    private List<Long> quantities;
}
