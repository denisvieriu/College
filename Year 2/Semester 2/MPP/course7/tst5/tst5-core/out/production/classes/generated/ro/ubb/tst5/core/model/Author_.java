package ro.ubb.tst5.core.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Author.class)
public abstract class Author_ extends ro.ubb.tst5.core.model.BaseEntity_ {

	public static volatile SetAttribute<Author, Book> books;
	public static volatile SingularAttribute<Author, String> name;

}

