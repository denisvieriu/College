package ro.ubb.pr10.web.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.ubb.pr10.core.model.Book;
import ro.ubb.pr10.web.dto.BookDto;

@Component
public class BookConverter  extends AbstractConverterBaseEntityConverter<Book, BookDto>  {
    private static final Logger log = LoggerFactory.getLogger(BookConverter.class);

    @Override
    public Book convertDtoToModel(BookDto bookDto) {
        return null;
    }

    @Override
    public BookDto convertModelToDto(Book book) {
        BookDto bookDto;

        log.trace("convertModelToDto: book={}", book);

        bookDto = BookDto.builder()
                .title(book.getTitle())
                .year(book.getYear())
                .build();

        bookDto.setId(book.getId());

        log.trace("convertModelToDto: bookDto={}", bookDto);
        return bookDto;

    }
}
