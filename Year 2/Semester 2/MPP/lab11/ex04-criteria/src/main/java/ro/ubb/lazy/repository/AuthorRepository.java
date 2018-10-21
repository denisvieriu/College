package ro.ubb.lazy.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.ubb.lazy.model.Author;

import java.util.List;

/**
 * Created by radu.
 */
public interface AuthorRepository extends CatalogRepository<Author, Long>,AuthorRepositoryCustom {
    @Query("select distinct a from Author a")
    @EntityGraph(value = "authorWithBooks", type = EntityGraph.EntityGraphType.LOAD)
    List<Author> findAllWithBooks();

    @Query("select distinct a from Author a")
    @EntityGraph(value = "authorWithBooksAndPublisher", type = EntityGraph.EntityGraphType.LOAD)
    List<Author> findAllWithBooksAndPublisher();
}
