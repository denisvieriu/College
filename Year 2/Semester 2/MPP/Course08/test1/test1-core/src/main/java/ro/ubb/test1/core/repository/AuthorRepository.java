package ro.ubb.test1.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubb.test1.core.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
