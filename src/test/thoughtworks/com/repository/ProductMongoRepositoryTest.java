package thoughtworks.com.repository;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.junit.Before;
import org.junit.Test;
import thoughtworks.com.domain.Price;
import thoughtworks.com.domain.Product;

import java.net.UnknownHostException;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class ProductMongoRepositoryTest {

    ProductRepositoryImpl productRepository;

    @Before
    public void setUp() throws Exception {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("test");
        productRepository = new ProductRepositoryImpl(db);
    }

    @Test
    public void should_create_and_get_product() throws UnknownHostException {
        Product apple = new Product("apple", "red apple");
        Price price = new Price(100, new Date());

        productRepository.createProduct(apple, price);
        Product product = productRepository.getProductById(apple.getId());
        assertThat(product.getName(), is("apple"));
    }

    @Test
    public void should_create_and_get_price_for_proruct() {
        Product apple = new Product("apple", "red apple");
        productRepository.createProduct(apple, new Price(100, new Date()));
        Price price = new Price(200, new Date());
        productRepository.createProductPrice(apple, price);
        Price priceGot = productRepository.getProductPriceById(apple, price.getId());
        assertThat(priceGot.getAmount(), is(200.0));
    }
}
