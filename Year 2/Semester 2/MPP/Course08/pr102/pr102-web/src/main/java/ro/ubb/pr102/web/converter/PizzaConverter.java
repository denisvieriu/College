package ro.ubb.pr102.web.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.ubb.pr102.core.model.BaseEntity;
import ro.ubb.pr102.core.model.Pizza;
import ro.ubb.pr102.web.dto.PizzaDto;

import java.util.stream.Collectors;

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
                .ingredients(pizza.getIngredients().stream().map(BaseEntity::getId).collect(Collectors.toSet()))
                .build();

        pizzaDto.setId(pizza.getId());

        log.trace("convertModelToDto: pizzaDto={}", pizzaDto);
        return pizzaDto;
    }

}
