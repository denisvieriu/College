package ro.ubb.pr10.web.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.ubb.pr10.core.model.Author;
import ro.ubb.pr10.core.model.BaseEntity;
import ro.ubb.pr10.core.model.Book;
import ro.ubb.pr10.web.dto.AuthorDto;
import ro.ubb.pr10.web.dto.BookDto;

import java.util.stream.Collectors;

@Component
public class AuthorConverter extends AbstractConverterBaseEntityConverter<Author, AuthorDto>  {
    private static final Logger log = LoggerFactory.getLogger(BookConverter.class);

    @Override
    public Author convertDtoToModel(AuthorDto authorDto) {
        return null;
    }

    @Override
    public AuthorDto convertModelToDto(Author author) {
        AuthorDto authorDto;

        log.trace("convertModelToDto: author={}", author);

        authorDto = AuthorDto.builder()
                .ssn(author.getSsn())
                .name(author.getName())
                .contact(author.getContact())
                .books(author.getBooks().stream().map(BaseEntity::getId).collect(Collectors.toSet()))
                .build();

        authorDto.setId(author.getId());

        log.trace("convertModelToDto: authorDto={}", authorDto);
        return authorDto;
    }
}


