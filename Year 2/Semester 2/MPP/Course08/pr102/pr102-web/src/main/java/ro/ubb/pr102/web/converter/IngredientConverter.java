package ro.ubb.pr102.web.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.ubb.pr102.core.model.BaseEntity;
import ro.ubb.pr102.core.model.Ingredient;
import ro.ubb.pr102.web.controller.IngredientController;
import ro.ubb.pr102.web.dto.IngredientDto;

import java.util.stream.Collectors;

@Component
public class IngredientConverter extends AbstractConverterBaseEntityConverter<Ingredient, IngredientDto> {
    private static final Logger log = LoggerFactory.getLogger(IngredientController.class);

    @Override
    public Ingredient convertDtoToModel(IngredientDto ingredientDto) {
        return null;
    }

    @Override
    public IngredientDto convertModelToDto(Ingredient ingredient) {
        IngredientDto ingredientDto;

        log.trace("convertModelToDto: ingredient={}", ingredient);

        ingredientDto = IngredientDto.builder()
                .name(ingredient.getName())
                .build();

        ingredientDto.setId(ingredient.getId());

        log.trace("convertModelToDto: ingredientDto={}", ingredientDto);
        return ingredientDto;
    }
}
