package ro.ubb.pr10.web.dto;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AuthorDto extends BaseDto {
    private String ssn;
    private String name;
    private String contact;
    private Set<Long> books;
}
