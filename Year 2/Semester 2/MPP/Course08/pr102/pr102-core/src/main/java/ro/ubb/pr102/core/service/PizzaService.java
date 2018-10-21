package ro.ubb.pr102.core.service;

import org.springframework.data.domain.Page;
import ro.ubb.pr102.core.model.Pizza;

import java.util.List;

public interface PizzaService {

    List<Pizza> findAll();

    List<Pizza> getPizzaService(Integer pageNumber);
}
