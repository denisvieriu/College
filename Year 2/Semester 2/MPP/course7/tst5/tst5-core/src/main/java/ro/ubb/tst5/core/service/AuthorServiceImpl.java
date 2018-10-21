package ro.ubb.tst5.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.tst5.core.model.Author;
import ro.ubb.tst5.core.repository.AuthorRepository;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private static final Logger log = LoggerFactory.getLogger(AuthorServiceImpl.class);

    private static final int PAGE_SIZE = 2;

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Author createAuthor(String name) {
        log.trace("createAuthor: name={}", name);

        Author author = Author.builder()
                .name(name)
                .build();

        author = authorRepository.save(author);

        log.trace("createAuthor: author={}", author);

        return author;
    }

    @Override
    public List<Author> finaAll() {
        log.trace("findAll -- method entered");

        List<Author> authors = authorRepository.findAllWithBooks();

        log.trace("findAll: authors={}", authors);

        return authors;
    }

    @Override
    @Transactional
    public List<Author> getAuthorService(Integer pageNumber) {
        log.trace("getAuthorService - pageNumber={}", pageNumber);

        PageRequest pageRequest = PageRequest.of(pageNumber - 1, PAGE_SIZE, Sort.Direction.ASC, "name");

        Page<Author> resultPage = authorRepository.findAll(pageRequest);

        List<Author> result = resultPage.getContent();

        log.trace("findAll -- method finished: result={}", result);

        return result;
    }

}
