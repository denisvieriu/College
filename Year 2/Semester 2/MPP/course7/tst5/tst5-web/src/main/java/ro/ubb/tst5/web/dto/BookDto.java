package ro.ubb.tst5.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class BookDto extends BaseDto {

    String title;
    Integer year;
    String author;
}
