package ro.ubb.tstt.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.tstt.core.model.Product;
import ro.ubb.tstt.core.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        log.trace("findAll -- method entered");

        List<Product> products = productRepository.findAll();

        log.trace("findAll: products={}", products);

        return products;
    }

    @Override
    @Transactional
    public Product updateProduct(Long productId, String name, int price, int stock) {
        log.trace("updateProduct: name={}, price={}, stock={}", name, price, stock);

        Optional<Product> product = productRepository.findById(productId);

        product.ifPresent(p ->{
            p.setName(name);
            p.setPrice(price);
            p.setStock(stock);
        });

        log.trace("updateProduct:product={}", product.get());
        return product.get();
    }
}
