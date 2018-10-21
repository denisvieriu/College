package ro.ubb.test1.core.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Person.class)
public abstract class Person_ {

	public static volatile ListAttribute<Person, Address> addresses;
	public static volatile SingularAttribute<Person, String> name;
	public static volatile SingularAttribute<Person, Long> id;

}

