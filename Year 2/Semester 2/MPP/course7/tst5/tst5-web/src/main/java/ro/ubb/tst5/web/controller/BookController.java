package ro.ubb.tst5.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.tst5.core.model.Book;
import ro.ubb.tst5.core.service.BookService;
import ro.ubb.tst5.web.converter.BookConverter;
import ro.ubb.tst5.web.dto.BookDto;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @Autowired
    private BookConverter bookConverter;

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    public BookDto createBook(
            @RequestBody final BookDto bookDto
    ) {
        log.trace("createBook: bookDto={}", bookDto);

        Book book = bookService.createBook(bookDto.getTitle(), bookDto.getYear(), bookDto.getAuthor());

        BookDto result = bookConverter.convertModelToDto(book);

        log.trace("createBook: result={}", result);

        return result;
    }

    @RequestMapping(value = "books", method = RequestMethod.GET)
    private List<BookDto> getBooks() {
        log.trace("getBooks -- method entered");

        List<Book> books = bookService.findAll();

        log.trace("getBooks: books={}", new ArrayList<>(bookConverter.convertModelsToDtos(books)));

        return new ArrayList<>(bookConverter.convertModelsToDtos(books));
    }
}
