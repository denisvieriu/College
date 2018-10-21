package ro.ubb.for10_1.core.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(LineItemPk.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"product", "order"})
@ToString(exclude = {"product", "order"})
@Data
public class LineItem implements Serializable {

    @Id
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @Id
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column
    private Integer quantity;
}
