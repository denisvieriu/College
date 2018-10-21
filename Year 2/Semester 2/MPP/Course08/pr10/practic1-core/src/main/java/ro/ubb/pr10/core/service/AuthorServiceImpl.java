package ro.ubb.pr10.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.pr10.core.model.Author;
import ro.ubb.pr10.core.repository.AuthorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
    private static final Logger log = LoggerFactory.getLogger(AuthorServiceImpl.class);

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public List<Author> findAll() {
        log.trace("findAll -- method entered");

        List<Author> authors = authorRepository.findAllWithBooks();

        log.trace("findAll: authors={}", authors);

        return authors;
    }

    @Override
    public List<Author> findBooksByAuthorAndYear(String authorName, String bookYear) {
        log.trace("findBooksByAuthorAndYear -- method entered: authorName={}, bookYear={}", authorName, bookYear);

        List<Author> authors = null;

        if (authorName != null && !authorName.equals("undefined")) {
            authors = authorRepository.findAllWithBooksByAutName(authorName);
        } else {
            authors = authorRepository.findAllWithBooks();
        }

        log.trace("findBooksByAuthorAndYear, before filter: authors={}", authors);

        if (bookYear != null && !bookYear.equals("undefined")) {
            int bookYearInt = Integer.parseInt(bookYear);
            log.trace("bookYearInt={}", bookYearInt);
            log.trace("books={}", authors.get(0).getBooks());

            for (Author author : authors) {
                author.getBooks().removeIf(b -> b.getYear() != bookYearInt);

            }
            authors = authors.stream()
                    .filter(a -> !a.getBooks().isEmpty())
                    .collect(Collectors.toList());
        }

        log.trace("findBooksByAuthorAndYear: authors={}", authors);

        return authors;
    }
}
