package ro.ubb.pr10.core.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.ubb.pr10.core.model.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("select distinct a from Author a")
    @EntityGraph(value = "authorWithBooks", type = EntityGraph.EntityGraphType.LOAD)
    List<Author> findAllWithBooks();

    @Query("select distinct a from Author a where a.name=:author_name")
    @EntityGraph(value = "authorWithBooks", type = EntityGraph.EntityGraphType.LOAD)
    List<Author> findAllWithBooksByAutName(@Param("author_name") String author_name);

}
