package ro.ubb.lazy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubb.lazy.model.Author;

/**
 * Created by radu.
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
