package ro.ubb.pr102.core.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Pizza.class)
public abstract class Pizza_ extends ro.ubb.pr102.core.model.BaseEntity_ {

	public static volatile SingularAttribute<Pizza, Float> price;
	public static volatile SingularAttribute<Pizza, String> name;
	public static volatile SingularAttribute<Pizza, String> description;
	public static volatile SetAttribute<Pizza, Ingredient> ingredients;

}

