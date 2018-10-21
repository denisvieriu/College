package ro.ubb.pr102.core.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import ro.ubb.pr102.core.model.Ingredient;

import java.util.List;

public interface IngredientRepository extends CustomRepository<Ingredient, Long> {

    @Query("select distinct i from Ingredient i")
    @EntityGraph(value = "ingredientWithPizzas", type = EntityGraph.EntityGraphType.LOAD)
    List<Ingredient> findAllWithPizzas();

}
