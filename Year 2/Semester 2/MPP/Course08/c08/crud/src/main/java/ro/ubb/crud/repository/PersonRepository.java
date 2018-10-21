package ro.ubb.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubb.crud.model.Person;

/**
 * Created by radu.
 */
public interface PersonRepository {

    void save(Person newPerson);

    Person findById(Long id);

    void update(Long id);

    void deleteById(Long id);

    void merge(Long id, Person person);
}
