package ro.ubb.tstt.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.tstt.core.model.ContactInfo;
import ro.ubb.tstt.core.model.LineItem;
import ro.ubb.tstt.core.model.Order;
import ro.ubb.tstt.core.model.Product;
import ro.ubb.tstt.core.repository.OrderRepository;
import ro.ubb.tstt.core.repository.ProductRepository;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Order> findAll() {
        log.trace("findAll -- method entered");

        List<Order> orders = orderRepository.findAll();

        log.trace("findAll: orders={}", orders);

        return orders;
    }

    @Override
    public Order createOrder(String email, String address) {
        log.trace("createOrder; email={}, address={}", email, address);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setAddress(address);
        contactInfo.setEmail(email);

        Order order = Order.builder()
                .date(new Date())
                .status(Order.Status.CART)
                .totalPrice(0)
                .contactInfo(contactInfo)
                .lineItems(new HashSet<>())
                .build();

        log.trace("successfully createOrder: order={}", order);

        order = orderRepository.save(order);

        log.trace("createOrder: order={}", order);

        return order;
    }

    @Override
    @Transactional
    public Order updateOrder(Long orderId, String address, String email, Set<Long> products) {
        log.trace("updateOrder: address={}, email={}", address, email);

        Optional<Order> product = orderRepository.findById(orderId);

        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setEmail(email);
        contactInfo.setAddress(address);

        HashSet<LineItem> lineItems = new HashSet<>();
        for (Long pr : products) {
            Product productt = productRepository.findById(pr).get();

            LineItem it = new LineItem();
            it.setOrder(orderRepository.findById(orderId).get());
            it.setProduct(productt);
            it.setQuantity(5);
            lineItems.add(it);
        }

        product.ifPresent(p ->{
            p.setContactInfo(contactInfo);
            p.setStatus(Order.Status.SUBMITTED);
            p.setLineItems(lineItems);
        });


        log.trace("updateOrder: product={}", product.get());
        return product.get();
    }
}
