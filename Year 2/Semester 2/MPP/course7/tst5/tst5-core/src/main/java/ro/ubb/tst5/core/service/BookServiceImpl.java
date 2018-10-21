package ro.ubb.tst5.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.tst5.core.model.Author;
import ro.ubb.tst5.core.model.Book;
import ro.ubb.tst5.core.repository.AuthorRepository;
import ro.ubb.tst5.core.repository.BookRepository;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book createBook(String title, Integer year, String authorName) {
        log.trace("createBook: title={}, year={}, authorName={}", title, year, authorName);

        Author author = authorRepository.findAllWithBooksByAutName(authorName).get(0);

        Book book = Book.builder()
                .title(title)
                .year(year)
                .author(author)
                .build();

        book = bookRepository.save(book);

        log.trace("createBook: book={}", book);

        return book;
    }

    @Override
    @Transactional
    public List<Book> findAll() {
        log.trace("findAll -- method entered");

        List<Book> books = bookRepository.findAllWithAuthors();

        log.trace("findAll: books={}", books);

        return books;
    }
}
