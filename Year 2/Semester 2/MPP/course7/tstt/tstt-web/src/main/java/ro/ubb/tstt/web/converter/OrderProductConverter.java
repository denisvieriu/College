package ro.ubb.tstt.web.converter;

import ro.ubb.tstt.core.model.LineItem;
import ro.ubb.tstt.web.dto.OrderProductDto;

public class OrderProductConverter extends AbstractConverter<LineItem, OrderProductDto> {
    @Override
    public LineItem convertDtoToModel(OrderProductDto orderProductDto) {
        return null;
    }

    @Override
    public OrderProductDto convertModelToDto(LineItem lineItem) {
        return OrderProductDto.builder()
                .orderId(lineItem.getOrder().getId())
                .productId(lineItem.getProduct().getId())
                .quantity(lineItem.getQuantity())
                .build();
    }
}
