package ro.ubb.for5_1.web.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.ubb.for5_1.core.model.Person;
import ro.ubb.for5_1.web.dto.PersonDto;

@Component
public class PersonConverter extends AbstractConverterBaseEntityConverter<Person, PersonDto> {
    private static final Logger log = LoggerFactory.getLogger(PersonConverter.class);

    @Override
    public Person convertDtoToModel(PersonDto personDto) {
        return null;
    }

    @Override
    public PersonDto convertModelToDto(Person person) {
        PersonDto personDto;

        log.trace("convertPersonToDto: person={}", person);

        personDto = PersonDto.builder()
                .name(person.getName())
                .ssn(person.getSsn())
                .build();

        personDto.setId(person.getId());

        log.trace("convertPersonToDto: personDto={}", personDto);

        return personDto;
    }
}
