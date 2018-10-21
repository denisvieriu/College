package ro.ubb.tstt.web.dto;

import lombok.*;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class OrderDto extends BaseDto{

    private String email;
    private String address;
    private Set<Long> products;
}
