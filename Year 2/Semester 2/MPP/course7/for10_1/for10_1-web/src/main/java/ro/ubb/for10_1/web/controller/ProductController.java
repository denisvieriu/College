package ro.ubb.for10_1.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.for10_1.core.model.Product;
import ro.ubb.for10_1.core.service.ProductService;
import ro.ubb.for10_1.web.converter.ProductConverter;
import ro.ubb.for10_1.web.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductConverter.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductConverter productConverter;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    private List<ProductDto> getProducts() {
        log.trace("getProducts -- method entered");

        List<Product> products = productService.findAll();

        log.trace("getProducts: products={}", new ArrayList<>(productConverter.convertModelsToDtos(products)));

        return new ArrayList<>(productConverter.convertModelsToDtos(products));
    }

}
