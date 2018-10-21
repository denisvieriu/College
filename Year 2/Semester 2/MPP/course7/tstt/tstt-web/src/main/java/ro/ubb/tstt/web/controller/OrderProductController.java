package ro.ubb.tstt.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.ubb.tstt.core.service.OrderService;
import ro.ubb.tstt.core.service.ProductService;
import ro.ubb.tstt.web.dto.OrderProductDto;

import java.util.Set;

@RestController
public class OrderProductController {
    private static final Logger log = LoggerFactory.getLogger(OrderProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/ops/{orderId}", method = RequestMethod.PUT)
    public Set<OrderProductDto> updateOrderProducts(
            @PathVariable final long orderId,
            @RequestBody final Set<OrderProductDto> products) {
        return null;
    }

}
