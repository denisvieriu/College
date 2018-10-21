package ro.ubb.for10_1.core.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Product.class)
public abstract class Product_ extends ro.ubb.for10_1.core.model.BaseEntity_ {

	public static volatile SingularAttribute<Product, Integer> price;
	public static volatile SingularAttribute<Product, String> name;
	public static volatile SetAttribute<Product, LineItem> orders;
	public static volatile SingularAttribute<Product, Integer> stock;

}

