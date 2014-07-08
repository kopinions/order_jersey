package thoughtworks.com.repository;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import thoughtworks.com.domain.Price;
import thoughtworks.com.domain.Product;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ProductRepositoryTest {
    @Test
    public void should_get_product_by_id() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        ProductRepository productRepository = sqlSessionFactory.openSession().getMapper(ProductRepository.class);
        Product apple = new Product("apple", "red apple");
        int effectRow = productRepository.createProduct(apple, new Price(10, new Date()));
        Product product = productRepository.getProductById(apple.getId());

        assertThat(effectRow, is(1));
        assertThat(product.getName(), is(apple.getName()));
        assertThat(product.getDescription(), is("red apple"));
        assertThat(product.getCurrentPrice().getAmount(), is(10.0));
    }

    @Test
    public void should_create_price_for_product() throws IOException, ParseException {
        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        ProductRepository productRepository = sqlSessionFactory.openSession().getMapper(ProductRepository.class);
        Product apple = new Product("apple", "red apple");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int effectRow = productRepository.createProduct(apple, new Price(10, dateFormat.parse("2014-01-01")));

        productRepository.createProductPrice(apple, new Price(100, new Date()));
        Product product = productRepository.getProductById(apple.getId());

        assertThat(effectRow, is(1));
        assertThat(product.getName(), is(apple.getName()));
        assertThat(product.getDescription(), is("red apple"));
        assertThat(product.getCurrentPrice().getAmount(), is(100.0));
    }

    @Test
    public void should_get_product_price_by_id() throws IOException, ParseException {
        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        ProductRepository productRepository = sqlSessionFactory.openSession().getMapper(ProductRepository.class);
        Product apple = new Product("apple", "red apple");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int effectRow = productRepository.createProduct(apple, new Price(10, dateFormat.parse("2014-01-01")));

        Price price = new Price(100, new Date());
        productRepository.createProductPrice(apple, price);

        assertThat(price.getId() > 0, is(true));

        System.out.println(price.getId());
        Price priceGetted = productRepository.getProductPriceById(apple, price.getId());

        assertThat(priceGetted.getAmount(), is(100.0));
    }
}
