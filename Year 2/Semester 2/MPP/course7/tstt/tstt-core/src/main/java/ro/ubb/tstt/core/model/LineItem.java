package ro.ubb.tstt.core.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="lineitem")
@IdClass(LineItemPk.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
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

    @Override
    public String toString() {
        return "LineItem{" +
                "product=" + product +
                ", order=" + order +
                ", quantity=" + quantity +
                '}';
    }

}
