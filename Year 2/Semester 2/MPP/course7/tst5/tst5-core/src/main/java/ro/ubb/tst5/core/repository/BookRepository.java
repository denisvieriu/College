package ro.ubb.tst5.core.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.ubb.tst5.core.model.Book;

import java.util.List;

public interface BookRepository  extends JpaRepository<Book, Long> {

    @Query("select distinct b from Book b")
    @EntityGraph(value = "booksWithAuthors", type = EntityGraph.EntityGraphType.LOAD)
    List<Book> findAllWithAuthors();

}
