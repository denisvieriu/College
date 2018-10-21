package ro.ubb.pr102.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.pr102.core.model.Pizza;
import ro.ubb.pr102.core.service.PizzaService;
import ro.ubb.pr102.web.converter.PizzaConverter;
import ro.ubb.pr102.web.dto.PizzaDto;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PizzaController {
    private static final Logger log = LoggerFactory.getLogger(PizzaController.class);


    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private PizzaConverter pizzaConverter;

    @RequestMapping(value = "/pizzas", method = RequestMethod.GET)
    public List<PizzaDto> getPizzas() {
        log.trace("getPizzas -- method entered");

        List<Pizza> result = pizzaService.findAll();

        log.trace("getPizzas: pizzas={}", new ArrayList<>(pizzaConverter.convertModelsToDtos(result)));

        return new ArrayList<>(pizzaConverter.convertModelsToDtos(result));
    }

    @RequestMapping(value = "/pizzas/{pageNumber}", method = RequestMethod.GET)
    public List<PizzaDto> getPizzasPage(
            @PathVariable Integer pageNumber) {
        log.trace("getPizzasPage: pageNumber={}", pageNumber);

        List<Pizza> result = pizzaService.getPizzaService(pageNumber);

        log.trace("getPizzas: pizzas={}", new ArrayList<>(pizzaConverter.convertModelsToDtos(result)));

        return new ArrayList<>(pizzaConverter.convertModelsToDtos(result));
    }

}
