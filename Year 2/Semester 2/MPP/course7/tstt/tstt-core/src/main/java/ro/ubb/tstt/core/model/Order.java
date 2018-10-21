package ro.ubb.tstt.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orderr")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = "lineItems")
@ToString(callSuper = true, exclude = "lineItems")
@Data
@Builder
public class Order extends BaseEntity<Long> {

    @Column(unique = true)
    @Temporal(TemporalType.DATE)
    private Date date;

    private int totalPrice;

    @Embedded
    private ContactInfo contactInfo;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status{
        CART, SUBMITTED
    };

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<LineItem> lineItems = new HashSet<>();

    public void addQuantity(Product product, Integer quantity) {
        LineItem lineItem = new LineItem();
        lineItem.setProduct(product);
        lineItem.setQuantity(quantity);
        lineItem.setOrder(this);
        lineItems.add(lineItem);
    }
}
