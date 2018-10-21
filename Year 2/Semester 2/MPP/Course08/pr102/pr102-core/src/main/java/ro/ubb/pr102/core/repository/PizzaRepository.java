package ro.ubb.pr102.core.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.ubb.pr102.core.model.Pizza;

import java.util.List;

public interface PizzaRepository extends CustomRepository<Pizza, Long> {

    @Query("select distinct p from Pizza p")
    @EntityGraph(value = "pizzaWithIngredients", type = EntityGraph.EntityGraphType.LOAD)
    List<Pizza> findAllWithIngredients();
}
