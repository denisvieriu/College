package ro.ubb.pr5.core.service;

import ro.ubb.pr5.core.model.Pizza;

import java.util.List;
import java.util.Optional;

public interface PizzaService {

    Optional<Pizza> findPizza(Long pizzaID);

    List<Pizza> findAll();

    Pizza createPizza(String name, String description, float price);

}
