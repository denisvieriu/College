package ro.ubb.tstt.core.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(LineItem.class)
public abstract class LineItem_ {

	public static volatile SingularAttribute<LineItem, Product> product;
	public static volatile SingularAttribute<LineItem, Integer> quantity;
	public static volatile SingularAttribute<LineItem, Order> order;

}

