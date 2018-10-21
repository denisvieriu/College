package ro.ubb.for10_1.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class Product extends BaseEntity<Long> {

    @Column
    private String name;

    @Column
    private int stock;

    @Column
    private int price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<LineItem> orders = new HashSet<>();
}
