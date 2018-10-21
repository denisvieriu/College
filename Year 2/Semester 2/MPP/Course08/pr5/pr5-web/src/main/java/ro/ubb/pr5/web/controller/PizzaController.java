package ro.ubb.pr5.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.pr5.core.model.Pizza;
import ro.ubb.pr5.core.service.PizzaService;
import ro.ubb.pr5.web.converter.PizzaConverter;
import ro.ubb.pr5.web.dto.PizzaDto;

@RestController
public class PizzaController {

    private static final Logger log = LoggerFactory.getLogger(PizzaController.class);

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private PizzaConverter pizzaConverter;

    @RequestMapping(value = "/pizzas", method = RequestMethod.POST)
    public PizzaDto createPizza(
            @RequestBody final PizzaDto pizzaDto) {
        log.trace("createPizza: pizzaDto={}", pizzaDto);

        Pizza pizza = pizzaService.createPizza(
                pizzaDto.getName(),
                pizzaDto.getDescription(),
                pizzaDto.getPrice()
        );

        PizzaDto result = pizzaConverter.convertModelToDto(pizza);

        log.trace("createPizza: result={}", pizza);

        return result;
    }
}
