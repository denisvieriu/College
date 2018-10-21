package ro.ubb.pr10.web.dto;

import lombok.*;

import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookDto extends BaseDto {
    private String title;
    private int year;
    private Set<Long> authors;
}
