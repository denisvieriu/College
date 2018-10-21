package ro.ubb.tstt.core.service;

import ro.ubb.tstt.core.model.Order;

import java.util.List;
import java.util.Set;

public interface OrderService {

    List<Order> findAll();

    Order createOrder(String email, String address);

    Order updateOrder(Long orderId, String address, String email, Set<Long> products);


}
