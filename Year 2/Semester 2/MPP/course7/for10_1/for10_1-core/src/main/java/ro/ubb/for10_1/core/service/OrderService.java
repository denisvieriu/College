package ro.ubb.for10_1.core.service;

import ro.ubb.for10_1.core.model.Order;

import java.util.List;
import java.util.Set;

public interface OrderService {

    Order createOrderWithEmail(String email);

    Order updateOrder(String email, String address, Integer totalPrice, Set<Long> products, List<Long> quantities);

}
