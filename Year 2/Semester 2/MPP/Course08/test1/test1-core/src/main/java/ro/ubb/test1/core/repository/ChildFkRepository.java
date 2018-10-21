package ro.ubb.test1.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubb.test1.core.model.ChildFK;

public interface ChildFkRepository extends JpaRepository<ChildFK, Long> {
}
