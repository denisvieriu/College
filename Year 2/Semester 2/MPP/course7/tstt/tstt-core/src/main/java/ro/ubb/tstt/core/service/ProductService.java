package ro.ubb.tstt.core.service;

import ro.ubb.tstt.core.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product updateProduct(Long productId, String name, int price, int stock);

}
