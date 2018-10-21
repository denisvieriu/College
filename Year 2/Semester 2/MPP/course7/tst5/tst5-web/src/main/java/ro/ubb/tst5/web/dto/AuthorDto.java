package ro.ubb.tst5.web.dto;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class AuthorDto extends BaseDto {
    private String name;
    private Set<Long> books;
}
