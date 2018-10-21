package ro.ubb.pr10.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.pr10.core.model.Book;
import ro.ubb.pr10.core.repository.BookRepository;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> findAll() {
        log.trace("findAll -- method entered");

        List<Book> books = bookRepository.findAll();

        log.trace("findAll: books={}", books);

        return books;
    }


}
