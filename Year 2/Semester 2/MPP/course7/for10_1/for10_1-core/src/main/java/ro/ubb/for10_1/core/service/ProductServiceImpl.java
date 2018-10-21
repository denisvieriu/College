package ro.ubb.for10_1.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.for10_1.core.model.Product;
import ro.ubb.for10_1.core.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Optional<Product> findProduct(Long productId) {
        return Optional.empty();
    }

    @Override
    public List<Product> findAll() {
        log.trace("findAll - repository");

        List<Product> products = productRepository.findAll();

        log.trace("findAll - method finished, products: {}", products);

        return products;
    }
}
