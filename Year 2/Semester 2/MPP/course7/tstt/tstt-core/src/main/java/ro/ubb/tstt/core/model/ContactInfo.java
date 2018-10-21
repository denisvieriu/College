package ro.ubb.tstt.core.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactInfo {

    private String email;

    private String address;

}
