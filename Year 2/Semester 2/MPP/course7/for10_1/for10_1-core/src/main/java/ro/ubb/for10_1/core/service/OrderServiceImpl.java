package ro.ubb.for10_1.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.for10_1.core.model.ContactInfo;
import ro.ubb.for10_1.core.model.Order;
import ro.ubb.for10_1.core.model.Product;
import ro.ubb.for10_1.core.repository.OrderRepository;
import ro.ubb.for10_1.core.repository.ProductRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Order createOrderWithEmail(String email) {
        log.trace("createOrderWithAddress: email={}", email);

        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setEmail(email);
        Order order = Order.builder()
                .contactInfo(contactInfo)
                .status(Order.State.CART)
                .build();

        order = orderRepository.save(order);

        log.trace("createOrderWithAddress: order={}", order);
        return order;
    }

    @Override
    @Transactional
    public Order updateOrder(String email, String address, Integer totalPrice, Set<Long> products, List<Long> quantitiesAsList) {
        log.trace("updateOrder: email={}, address={}", email, address);

        List<Order> orders = orderRepository.findAll();
        Order o = null;
        for (Order order1 : orders) {
            if (order1.getContactInfo().getEmail() != null) {
                if (order1.getContactInfo().getEmail().equals(email)) {
                    o = order1;
                    break;
                }
            }
        }

        if (o == null) {
            log.trace("Not found by email");
        } else {
            log.trace("Found an match");
        }

        assert o != null;
        log.trace("Assert passed");

        o.getContactInfo().setAddress(address);
        o.setDate(new Date());
        o.setTotalPrice(totalPrice);
        o.setStatus(Order.State.SUBMITTED);

        log.trace("Successfully set all fields");

        if (quantitiesAsList != null && products != null) {

            List<Product> productList = productRepository.findAllById(products);

            log.trace("Successfully got all products: productList={}", productList);
            o.addQuantities(productList, quantitiesAsList);
        }

        log.trace("updateOrder: order={}", o);

        return o;
    }
}
