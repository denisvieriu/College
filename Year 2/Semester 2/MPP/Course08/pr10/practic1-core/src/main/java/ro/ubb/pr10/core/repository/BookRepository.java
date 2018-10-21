package ro.ubb.pr10.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubb.pr10.core.model.Book;

public interface BookRepository  extends JpaRepository<Book, Long> {
}
