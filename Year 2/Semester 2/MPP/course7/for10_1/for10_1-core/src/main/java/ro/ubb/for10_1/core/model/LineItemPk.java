package ro.ubb.for10_1.core.model;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class LineItemPk implements Serializable {
    private Product product;
    private Order order;
}
