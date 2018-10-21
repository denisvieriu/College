package ro.ubb.for10_1.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "orderr")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = "products")
@Builder
@Getter
@Setter
public class Order extends BaseEntity<Long> {

    public enum State {CART, SUBMITTED};

    @Column(unique = true)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column
    private int totalPrice;

    @Embedded
    private ContactInfo contactInfo;

    @Enumerated(EnumType.STRING)
    private State status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<LineItem> products = new HashSet<>();

    public void addQuantity(Product product, Integer quantity) {
        System.out.println("product=" + product + " , quantitiy=" + quantity);
        System.out.println(">> HERE 1");
        LineItem lineItem = new LineItem();
        System.out.println(">> HERE 2: lineItem=" + lineItem);
        lineItem.setProduct(product);
        System.out.println(">> HERE 3: lineItem=" + lineItem);
        lineItem.setQuantity(quantity);
        System.out.println(">> HERE 4: lineItem=" + lineItem);
        System.out.println(this); // crapa la primul this (si ar trebui sa fie ca prima oara
        lineItem.setOrder(this);
        System.out.println(">> HERE 5; lineitem=" + lineItem); // aici a doua oara crapa, cand dau setorder(this) -> this ala e empty
        products.add(lineItem);
        System.out.println(">> HERE 6");
        System.out.println();
    }

    public void addQuantities(List<Product> products, List<Long> quantities){
        for (int i = 0; i < products.size(); i++) {
            Order order = new Order(); // cum faci deep copy aici?
            // stai ca iti arat unde crapa
            System.out.println(this);
            this.addQuantity(products.get(i), quantities.get(i).intValue());
        }
    }




}
