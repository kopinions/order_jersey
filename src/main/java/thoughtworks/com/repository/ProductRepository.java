package thoughtworks.com.repository;

import org.bson.types.ObjectId;
import thoughtworks.com.domain.Price;
import thoughtworks.com.domain.Product;

public interface ProductRepository {
    Product getProductById(ObjectId productId);

    int createProduct(Product product, Price price);

    Price getProductPriceById(Product product, int priceId);

    ObjectId createProductPrice(Product product, Price price);
}
