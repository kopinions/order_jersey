package thoughtworks.com.repository;

import thoughtworks.com.domain.Product;

public interface ProductRepository {
    Product getProductById(int productId);

    int createProduct();
}
