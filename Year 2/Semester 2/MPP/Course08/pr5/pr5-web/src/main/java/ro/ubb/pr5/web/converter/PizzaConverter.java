package ro.ubb.pr5.web.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.ubb.pr5.core.model.Pizza;
import ro.ubb.pr5.web.dto.PizzaDto;

@Component
public class PizzaConverter extends AbstractConverterBaseEntityConverter<Pizza, PizzaDto> {
    private static final Logger log = LoggerFactory.getLogger(PizzaConverter.class);

    @Override
    public Pizza convertDtoToModel(PizzaDto pizzaDto) {
        return null;
    }

    @Override
    public PizzaDto convertModelToDto(Pizza pizza) {
        PizzaDto pizzaDto;

        log.trace("convertModelToDto: pizza={}", pizza);

        pizzaDto = PizzaDto.builder()
                .name(pizza.getName())
                .description(pizza.getDescription())
                .price(pizza.getPrice())
                .build();

        pizzaDto.setId(pizza.getId());

        log.trace("convertModelToDto: pizzaDto={}", pizzaDto);

        return pizzaDto;
    }
}
