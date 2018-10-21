package ro.ubb.pr102.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.pr102.core.model.Ingredient;
import ro.ubb.pr102.core.service.IngredientService;
import ro.ubb.pr102.web.converter.IngredientConverter;
import ro.ubb.pr102.web.dto.IngredientDto;

import java.util.ArrayList;
import java.util.List;

@RestController
public class IngredientController {

    private static final Logger log = LoggerFactory.getLogger(IngredientController.class);

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private IngredientConverter ingredientConverter;

    @RequestMapping(value = "/ingredients", method = RequestMethod.GET)
    public List<IngredientDto> getIngredients() {
        log.trace("getIngredients -- method entered");

        List<Ingredient> result = ingredientService.findAll();

        log.trace("getIngredients: ingredients={}", new ArrayList<>(ingredientConverter.convertModelsToDtos(result)));

        return new ArrayList<>(ingredientConverter.convertModelsToDtos(result));
    }
}
