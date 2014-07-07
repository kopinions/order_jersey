package thoughtworks.com.repository;

import thoughtworks.com.domain.Price;
import thoughtworks.com.domain.Product;

public interface ProductRepository {
    Product getProductById(int productId);

    int createProduct(Product product, Price price);
}
