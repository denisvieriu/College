package ro.ubb.pr10.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.pr10.core.model.Book;
import ro.ubb.pr10.core.service.BookService;
import ro.ubb.pr10.web.converter.BookConverter;
import ro.ubb.pr10.web.dto.BookDto;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {
    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @Autowired
    private BookConverter bookConverter;

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public List<BookDto> getBooks() {
        log.trace("getBooks -- method entered");

        List<Book> result = bookService.findAll();

        log.trace("getMovies: movies={}", new ArrayList<>(bookConverter.convertModelsToDtos(result)));

        return new ArrayList<>(bookConverter.convertModelsToDtos(result));
    }
}
