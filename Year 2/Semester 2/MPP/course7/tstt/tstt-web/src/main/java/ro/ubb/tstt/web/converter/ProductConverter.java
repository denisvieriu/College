package ro.ubb.tstt.web.converter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.ubb.tstt.core.model.BaseEntity;
import ro.ubb.tstt.core.model.Product;
import ro.ubb.tstt.web.dto.ProductDto;

import java.util.stream.Collectors;

@Component
public class ProductConverter extends AbstractConverterBaseEntityConverter<Product, ProductDto> {
    private static final Logger log = LoggerFactory.getLogger(ProductConverter.class);

    @Override
    public Product convertDtoToModel(ProductDto productDto) {
        return null;
    }

    @Override
    public ProductDto convertModelToDto(Product product) {
        log.trace("convertModelToDto: product={}", product);

        ProductDto productDto;

        productDto = ProductDto.builder()
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();

        productDto.setId(product.getId());

        log.trace("convertModelToDto: productDto={}", productDto);
        return productDto;
    }
}
