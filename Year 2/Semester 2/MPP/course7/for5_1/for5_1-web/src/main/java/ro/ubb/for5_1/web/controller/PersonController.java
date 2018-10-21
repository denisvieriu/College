package ro.ubb.for5_1.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.for5_1.core.model.Person;
import ro.ubb.for5_1.core.service.PersonService;
import ro.ubb.for5_1.web.converter.PersonConverter;
import ro.ubb.for5_1.web.dto.PersonDto;

@RestController
public class PersonController {
    private static final Logger log = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonConverter personConverter;

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/persons", method = RequestMethod.POST)
    public PersonDto createPerson(@RequestBody final PersonDto personDto) {
        log.trace("createPerson: personDto={}", personDto);

        Person person = personService.createPerson(
                personDto.getName(),
                personDto.getSsn()
        );

        PersonDto result = personConverter.convertModelToDto(person);

        log.trace("createPerson: result={}", personDto);

        return personDto;
    }

}
