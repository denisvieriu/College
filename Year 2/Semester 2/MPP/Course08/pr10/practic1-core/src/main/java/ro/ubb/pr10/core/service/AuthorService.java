package ro.ubb.pr10.core.service;

import ro.ubb.pr10.core.model.Author;

import java.util.List;

public interface AuthorService {

    List<Author> findAll();

    public List<Author> findBooksByAuthorAndYear(String authorName, String bookYear);
}
