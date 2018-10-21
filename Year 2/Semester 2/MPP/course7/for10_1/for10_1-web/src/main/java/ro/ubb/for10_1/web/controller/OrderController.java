package ro.ubb.for10_1.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.ubb.for10_1.core.model.Order;
import ro.ubb.for10_1.core.service.OrderService;
import ro.ubb.for10_1.web.converter.OrderConverter;
import ro.ubb.for10_1.web.dto.OrderDto;

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

        Order order = orderService.createOrderWithEmail(orderDto.getEmail());

        OrderDto result = orderConverter.convertModelToDto(order);

        log.trace("createOrder: result={}", result);

        return result;
    }

    @RequestMapping(value="/orders/{orderId}", method = RequestMethod.PUT)
    public OrderDto updateOrder(
            @PathVariable final Long orderId,
            @RequestBody final OrderDto orderDto
    ) {
        log.trace("updateOrder: orderDto={}", orderDto);

        Order order = orderService.updateOrder(orderDto.getEmail(),
                orderDto.getAddress(), orderDto.getTotalPrice(), orderDto.getProducts(), orderDto.getQuantities());

        OrderDto result = orderConverter.convertModelToDto(order);

        log.trace("updateOrder: result={}", result);

        return result;
    }

}
