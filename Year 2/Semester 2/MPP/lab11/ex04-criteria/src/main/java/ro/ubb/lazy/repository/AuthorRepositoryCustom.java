package ro.ubb.lazy.repository;

import ro.ubb.lazy.model.Author;

import java.util.List;

/**
 * Created by radu.
 */
public interface AuthorRepositoryCustom {
    List<Author> findAllWithBooksAndPublisherJPQL();

    List<Author> findAllWithBooksAndPublisherCriteriaAPI();
}
