package ro.ubb.tst5.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.ubb.tst5.core.model.Author;
import ro.ubb.tst5.core.service.AuthorService;
import ro.ubb.tst5.web.converter.AuthorConverter;
import ro.ubb.tst5.web.dto.AuthorDto;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AuthorController {
    private static final Logger log = LoggerFactory.getLogger(AuthorController.class);

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorConverter authorConverter;

    @RequestMapping(value = "/authors", method = RequestMethod.POST)
    public AuthorDto createAuthor(
            @RequestBody final AuthorDto authorDto
    ) {
        log.trace("createAuthor: authorDto={}", authorDto);

        Author author = authorService.createAuthor(authorDto.getName());

        AuthorDto result = authorConverter.convertModelToDto(author);

        log.trace("createAuthor: result={}", result);

        return result;
    }

    @RequestMapping(value = "authors", method = RequestMethod.GET)
    private List<AuthorDto> getAuthors() {
        log.trace("getAuthors -- method entered");

        List<Author> authors = authorService.finaAll();

        log.trace("getAuthors: authors={}", new ArrayList<>(authorConverter.convertModelsToDtos(authors)));

        return new ArrayList<>(authorConverter.convertModelsToDtos(authors));
    }

    @RequestMapping(value = "/authors/{pageNumber}", method = RequestMethod.GET)
    public List<AuthorDto> getAuthorsPage(
            @PathVariable Integer pageNumber
    ) {
        log.trace("getAuthorsPage: pageNumber={}", pageNumber);

        List<Author> result = authorService.getAuthorService(pageNumber);

        log.trace("getAuthors: authors={}", new ArrayList<>(authorConverter.convertModelsToDtos(result)));

        return new ArrayList<>(authorConverter.convertModelsToDtos(result));
    }
}
