package ro.ubb.for10_1.core.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@Data
@ToString
public class ContactInfo {

    @Column
    private String email;

    @Column
    private String address;
}
