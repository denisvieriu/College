package ro.ubb.delete.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubb.delete.model.Student;

/**
 * Created by radu.
 */
public interface StudentRepository extends JpaRepository<Student, Long> {
}
