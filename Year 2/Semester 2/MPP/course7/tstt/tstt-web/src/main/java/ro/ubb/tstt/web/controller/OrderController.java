package ro.ubb.tstt.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.ubb.tstt.core.model.Order;
import ro.ubb.tstt.core.service.OrderService;
import ro.ubb.tstt.web.converter.OrderConverter;
import ro.ubb.tstt.web.dto.OrderDto;

@RestController
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderConverter orderConverter;

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public OrderDto createOrder(
            @RequestBody final OrderDto orderDto
    ) {
        log.trace("createOrder: orderDto={}", orderDto);

        Order order = orderService.createOrder(
                orderDto.getEmail(), orderDto.getAddress()
        );

        OrderDto result = orderConverter.convertModelToDto(order);

        log.trace("createOrder: result={}", order);
        return result;
    }

    @RequestMapping(value = "/orders/{orderId}", method = RequestMethod.PUT)
    public OrderDto updateProduct(
            @PathVariable final Long orderId,
            @RequestBody final OrderDto orderDto) {
        log.trace("updateOrder: orderId={}, productDtoMap={}", orderId, orderDto);

        Order updateOrder = orderService.updateOrder(orderId,
                orderDto.getAddress(),
                orderDto.getEmail(),
                orderDto.getProducts()
                );

        OrderDto result = orderConverter.convertModelToDto(updateOrder);

        log.trace("updateProduct: result={}", result);

        return result;
    }
}
