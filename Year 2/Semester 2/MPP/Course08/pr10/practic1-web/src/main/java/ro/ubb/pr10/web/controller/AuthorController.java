package ro.ubb.pr10.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import ro.ubb.pr10.core.model.Author;
import ro.ubb.pr10.core.service.AuthorService;
import ro.ubb.pr10.web.converter.AuthorConverter;
import ro.ubb.pr10.web.dto.AuthorDto;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AuthorController {
    private static final Logger log = LoggerFactory.getLogger(AuthorController.class);

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorConverter authorConverter;

    @RequestMapping(value = "/authors", method = RequestMethod.GET)
    public List<AuthorDto> getAuthors() {
        log.trace("getAuthors -- method entered");

        List<Author> result = authorService.findAll();

        log.trace("getAuthors: authors={}", new ArrayList<>(authorConverter.convertModelsToDtos(result)));

        return new ArrayList<>(authorConverter.convertModelsToDtos(result));
    }

    @RequestMapping(value = "/authors/{authorName}/{bookYear}", method = RequestMethod.GET)
    public List<AuthorDto> getAuthorsByName(
            @PathVariable final String authorName,
            @PathVariable final String bookYear) {
        log.trace("Get authors: authorName={}, bookYear={}", authorName, bookYear);


        List<Author> result = this.authorService.findBooksByAuthorAndYear(authorName, bookYear);

        log.trace("getAuthors: authors={}", new ArrayList<>(authorConverter.convertModelsToDtos(result)));

        return new ArrayList<>(authorConverter.convertModelsToDtos(result));
    }
}
