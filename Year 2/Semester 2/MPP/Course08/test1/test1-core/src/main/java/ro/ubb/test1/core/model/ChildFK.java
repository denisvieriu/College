package ro.ubb.test1.core.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "ChildFk")
@Setter
@Getter
public class ChildFK extends PersonFK{


    private boolean rechargable;
}
