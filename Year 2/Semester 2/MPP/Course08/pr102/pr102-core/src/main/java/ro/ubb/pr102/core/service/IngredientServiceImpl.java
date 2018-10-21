package ro.ubb.pr102.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.pr102.core.model.Ingredient;
import ro.ubb.pr102.core.repository.IngredientRepository;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {
    private static final Logger log = LoggerFactory.getLogger(IngredientServiceImpl.class);

    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    public List<Ingredient> findAll() {
        log.trace("findAll -- method entered");

        List<Ingredient> result = ingredientRepository.findAllWithPizzas();

        log.trace("findAll -- method finished: result={}", result);

        return result;
    }
}
