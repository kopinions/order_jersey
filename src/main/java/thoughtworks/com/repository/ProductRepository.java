package thoughtworks.com.repository;

import org.bson.types.ObjectId;
import thoughtworks.com.domain.Price;
import thoughtworks.com.domain.Product;

public interface ProductRepository {
    Product getProductById(ObjectId productId);

    Product createProduct(Product product);

    Price getProductPriceById(Product product, ObjectId priceId);

    Price createProductPrice(Product product, Price price);
}
