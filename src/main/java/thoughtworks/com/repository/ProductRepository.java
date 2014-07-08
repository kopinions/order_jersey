package thoughtworks.com.repository;

import org.apache.ibatis.annotations.Param;
import thoughtworks.com.domain.Price;
import thoughtworks.com.domain.Product;

public interface ProductRepository {
    Product getProductById(@Param("productId") int productId);

    int createProduct(@Param("product")Product product, @Param("price") Price price);

    Price getProductPriceById(@Param("product") Product product, @Param("priceId") int priceId);

    int createProductPrice(@Param("product")Product product, @Param("price") Price price);
}
