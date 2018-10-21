package ro.ubb.pr102.core.model;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@NamedEntityGraph(name = "pizzaWithIngredients",
        attributeNodes = @NamedAttributeNode(value = "ingredients"))

@Entity
@Table(name = "pizza")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true, exclude = "ingredients")
@Builder
public class Pizza extends BaseEntity<Long> {

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private float price;

    @OneToMany(mappedBy = "pizza", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Ingredient> ingredients = new HashSet<>();
}
