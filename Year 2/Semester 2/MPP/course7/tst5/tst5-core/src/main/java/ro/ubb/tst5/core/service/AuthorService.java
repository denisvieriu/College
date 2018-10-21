package ro.ubb.tst5.core.service;

import ro.ubb.tst5.core.model.Author;

import java.util.List;

public interface AuthorService {

    Author createAuthor(String name);

    List<Author> finaAll();

    List<Author> getAuthorService(Integer pageNumber);
}
