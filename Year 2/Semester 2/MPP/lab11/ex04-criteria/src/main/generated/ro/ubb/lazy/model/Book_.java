package ro.ubb.lazy.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Book.class)
public abstract class Book_ extends ro.ubb.lazy.model.BaseEntity_ {

	public static volatile SingularAttribute<Book, Author> author;
	public static volatile SingularAttribute<Book, Publisher> publisher;
	public static volatile SingularAttribute<Book, String> title;

}

