package ro.ubb.pr102.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NamedEntityGraphs({
        @NamedEntityGraph(name = "ingredientWithPizzas",
        attributeNodes = @NamedAttributeNode(value = "pizza"))
})
@Entity
@Table(name = "ingredient")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true, exclude = "pizza")
@Builder
public class Ingredient extends BaseEntity<Long> {

    @Column
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Pizza pizza;
}
