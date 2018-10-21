package ro.ubb.tstt.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.ubb.tstt.core.model.Product;
import ro.ubb.tstt.core.service.ProductService;
import ro.ubb.tstt.web.converter.ProductConverter;
import ro.ubb.tstt.web.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductConverter productConverter;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<ProductDto> getProducts() {
        log.trace("getProducts -- method entered");

        List<Product> result = productService.findAll();

        log.trace("getProducts:result={}", new ArrayList<>(productConverter.convertModelsToDtos(result)));

        return new ArrayList<>(productConverter.convertModelsToDtos(result));
    }

    @RequestMapping(value = "/products/{productId}", method = RequestMethod.PUT)
    public ProductDto updateProduct(
            @PathVariable final Long productId,
            @RequestBody final ProductDto productDto) {
        log.trace("updateProudct: productId={}, productDtoMap={}", productId, productDto);

        Product updateProduct = productService.updateProduct(productId,
                productDto.getName(),
                productDto.getPrice(),
                productDto.getStock());

        ProductDto result = productConverter.convertModelToDto(updateProduct);

        log.trace("updateProduct: result={}", result);

        return result;
    }
}
