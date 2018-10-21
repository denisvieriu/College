package ro.ubb.tstt.web.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.ubb.tstt.core.model.Order;
import ro.ubb.tstt.web.dto.OrderDto;

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

        OrderDto orderDto = OrderDto.builder()
                .email(order.getContactInfo().getEmail())
                .address(order.getContactInfo().getAddress())
                .build();

        orderDto.setId(order.getId());

        log.trace("convertModelToDto: orderDto={}", orderDto);
        return orderDto;
    }
}
