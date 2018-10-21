package ro.ubb.for10_1.web.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.ubb.for10_1.core.model.Order;
import ro.ubb.for10_1.web.dto.OrderDto;
import ro.ubb.for10_1.web.dto.ProductDto;

@Component
public class OrderConverter extends AbstractConverterBaseEntityConverter<Order, OrderDto> {

    private static final Logger log = LoggerFactory.getLogger(OrderConverter.class);


    @Override
    public Order convertDtoToModel(OrderDto orderDto) {
        return null;
    }

    @Override
    public OrderDto convertModelToDto(Order order) {
        log.trace("convertModelToDto: order={}", order);

        OrderDto orderDto;

        orderDto = OrderDto.builder()
                .address(order.getContactInfo().getAddress())
                .email(order.getContactInfo().getEmail())
                .date(order.getDate())
                .status(order.getStatus())
                .totalPrice(order.getTotalPrice())
                .build();

        orderDto.setId(order.getId());

        log.trace("convertModelToDto: orderDto={}", orderDto);

        return orderDto;
    }
}
