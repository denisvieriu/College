package ro.ubb.tstt.core.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import ro.ubb.tstt.core.model.Order.Status;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Order.class)
public abstract class Order_ extends ro.ubb.tstt.core.model.BaseEntity_ {

	public static volatile SingularAttribute<Order, Date> date;
	public static volatile SetAttribute<Order, LineItem> lineItems;
	public static volatile SingularAttribute<Order, ContactInfo> contactInfo;
	public static volatile SingularAttribute<Order, Integer> totalPrice;
	public static volatile SingularAttribute<Order, Status> status;

}

