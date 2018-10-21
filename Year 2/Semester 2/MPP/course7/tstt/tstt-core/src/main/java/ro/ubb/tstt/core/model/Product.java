package ro.ubb.tstt.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = "lineItems")
@ToString(callSuper = true, exclude = "lineItems")
@Data
@Builder
public class Product extends BaseEntity<Long> {

    @Column(unique = true)
    private String name;

    private int price;

    private int stock;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<LineItem> lineItems = new HashSet<>();

    public Set<Order> getLineItems2() {
        return Collections.unmodifiableSet(
                this.lineItems.stream().
                        map(LineItem::getOrder).
                        collect(Collectors.toSet())
        );
    }
}
