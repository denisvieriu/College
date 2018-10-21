package ro.ubb.tst5.web.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.ubb.tst5.core.model.Author;
import ro.ubb.tst5.core.model.BaseEntity;
import ro.ubb.tst5.web.dto.AuthorDto;

import java.util.stream.Collectors;

@Component
public class AuthorConverter extends AbstractConverterBaseEntityConverter<Author, AuthorDto> {
    private static final Logger log = LoggerFactory.getLogger(AuthorConverter.class);

    @Override
    public Author convertDtoToModel(AuthorDto authorDto) {
        return null;
    }

    @Override
    public AuthorDto convertModelToDto(Author author) {
        log.trace("convertModelToDto: author={}", author);

        AuthorDto authorDto;

        if (author.getBooks() != null) {
            authorDto = AuthorDto.builder()
                    .name(author.getName())
                    .books(author.getBooks().stream().map(BaseEntity::getId).collect(Collectors.toSet()))
                    .build();
        }
        else {
            authorDto = AuthorDto.builder()
                    .name(author.getName())
                    .build();
        }

        authorDto.setId(author.getId());

        log.trace("convertModelToDto: authorDto={}", authorDto);
        return authorDto;
    }
}
