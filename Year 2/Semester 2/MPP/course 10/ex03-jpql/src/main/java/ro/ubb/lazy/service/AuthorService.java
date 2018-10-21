package ro.ubb.lazy.service;

import ro.ubb.lazy.model.Author;

import java.util.List;

/**
 * Created by radu.
 */
public interface AuthorService {
    List<Author> findAll();
}
