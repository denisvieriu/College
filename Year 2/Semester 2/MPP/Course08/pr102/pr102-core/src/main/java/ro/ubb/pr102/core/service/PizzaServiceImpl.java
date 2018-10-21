package ro.ubb.pr102.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ro.ubb.pr102.core.model.Pizza;
import ro.ubb.pr102.core.repository.PizzaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PizzaServiceImpl implements PizzaService {
    private static final Logger log = LoggerFactory.getLogger(PizzaServiceImpl.class);

    private static final int PAGE_SIZE = 2;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Override
    public List<Pizza> findAll() {
        log.trace("findAll -- method entered");

        List<Pizza> result = pizzaRepository.findAllWithIngredients();

        log.trace("findAll -- method finished: result={}", result);

        return result;
    }

    @Override
    @Transactional
    public List<Pizza> getPizzaService(Integer pageNumber) {
        log.trace("getPizzaService - pageNumber={}", pageNumber);

        PageRequest pageRequest = PageRequest.of(pageNumber - 1, PAGE_SIZE, Sort.Direction.ASC, "id");

        Page<Pizza> resultPage = pizzaRepository.findAll(pageRequest);

        List<Pizza> result = resultPage.getContent();

        log.trace("findAll -- method finished: result={}", result);

        return result;
    }
}
