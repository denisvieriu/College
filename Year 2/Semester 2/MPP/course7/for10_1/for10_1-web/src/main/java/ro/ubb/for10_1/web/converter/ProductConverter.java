package ro.ubb.for10_1.web.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.ubb.for10_1.core.model.Product;
import ro.ubb.for10_1.web.dto.ProductDto;

@Component
public class ProductConverter extends AbstractConverterBaseEntityConverter<Product, ProductDto>  {
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
                .stock(product.getStock())
                .price(product.getPrice())
                .build();

        productDto.setId(product.getId());

        log.trace("convertModelToDto: productDto={}", productDto);

        return productDto;
    }
}
