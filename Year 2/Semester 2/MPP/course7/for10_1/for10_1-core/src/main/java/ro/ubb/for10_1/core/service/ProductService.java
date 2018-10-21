package ro.ubb.for10_1.core.service;

import ro.ubb.for10_1.core.model.Product;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<Product> findProduct(Long productId);

    List<Product> findAll();
}
