package ro.ubb.test1.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubb.test1.core.model.Person;

import javax.transaction.Transactional;

@Transactional
public interface PersonRepository extends JpaRepository<Person, Long> {
}
