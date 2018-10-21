package ro.ubb.test1.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubb.test1.core.model.PersonFK;

public interface PersonFkRepository extends JpaRepository<PersonFK, Long> {
}
