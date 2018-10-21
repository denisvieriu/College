package ro.ubb.pr5.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.pr5.core.model.Pizza;
import ro.ubb.pr5.core.repository.PizzaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaServiceImpl implements PizzaService {
    private static final Logger log = LoggerFactory.getLogger(PizzaServiceImpl.class);

    @Autowired
    private PizzaRepository pizzaRepository;

    @Override
    public Optional<Pizza> findPizza(Long pizzaID) {
        return Optional.empty();
    }

    @Override
    public List<Pizza> findAll() {
        return null;
    }

    @Override
    public Pizza createPizza(String name, String description, float price) {
        log.trace("createPizza: name={}, description={}, price={}", name, description, price);

        Pizza pizza = Pizza.builder()
                .name(name)
                .description(description)
                .price(price)
                .build();

        pizza = pizzaRepository.save(pizza);

        log.trace("createPizza: pizza={}", pizza);

        return pizza;
    }
}
