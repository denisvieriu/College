package ro.ubb.for5_1.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.for5_1.core.model.Person;
import ro.ubb.for5_1.core.repository.PersonRepository;

@Service
public class PersonServiceImpl implements PersonService {
    private static final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Person createPerson(String name, String ssn) {
        log.trace("createPerson: name={}, ssn={}", name, ssn);

        Person person = Person.builder()
                .name(name)
                .ssn(ssn)
                .build();

        person = personRepository.save(person);

        log.trace("createPerson: person={}", person);

        return person;
    }
}
