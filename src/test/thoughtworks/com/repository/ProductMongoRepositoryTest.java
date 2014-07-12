package thoughtworks.com.repository;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.junit.Before;
import org.junit.Test;
import thoughtworks.com.domain.Price;
import thoughtworks.com.domain.Product;
import thoughtworks.com.domain.ProductBuilder;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
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
        Product apple = new ProductBuilder().name("apple").description("red apple")
                .currentPrice(new Price(100, new Date())).build();

        productRepository.createProduct(apple);
        Product product = productRepository.getProductById(apple.getId());
        assertThat(product, is(notNullValue()));
        assertThat(product.getName(), is("apple"));
        assertThat(product.getCurrentPrice().getAmount(), is(100.0));
    }

    @Test
    public void should_create_and_get_price_for_proruct() {
        Product apple = new ProductBuilder().name("apple").description("red apple").currentPrice(new Price(100, new Date())).build();
        productRepository.createProduct(apple);
        Price price = new Price(200, new Date());
        Price createdPrice = productRepository.createProductPrice(apple, price);
        Price priceGot = productRepository.getProductPriceById(apple, createdPrice.getId());
        assertThat(priceGot.getAmount(), is(200.0));
    }

    @Test
    public void should_create_price_and_set_current_price_when_date_is_newer() throws ParseException {
        Product apple = new ProductBuilder().name("apple").description("red apple").currentPrice(new Price(100, new SimpleDateFormat("yyyy-MM-dd").parse("2014-01-01"))).build();
        productRepository.createProduct(apple);
        productRepository.createProductPrice(apple, new Price(200, new Date()));
        Product findApple = productRepository.getProductById(apple.getId());
        assertThat(findApple.getCurrentPrice().getAmount(), is(200.0));
    }
}
