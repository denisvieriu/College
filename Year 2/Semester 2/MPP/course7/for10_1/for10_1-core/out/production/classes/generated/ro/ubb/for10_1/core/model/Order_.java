package ro.ubb.for10_1.core.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import ro.ubb.for10_1.core.model.Order.State;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Order.class)
public abstract class Order_ extends ro.ubb.for10_1.core.model.BaseEntity_ {

	public static volatile SingularAttribute<Order, Date> date;
	public static volatile SingularAttribute<Order, ContactInfo> contactInfo;
	public static volatile SingularAttribute<Order, Integer> totalPrice;
	public static volatile SingularAttribute<Order, State> status;
	public static volatile SetAttribute<Order, LineItem> products;

}

