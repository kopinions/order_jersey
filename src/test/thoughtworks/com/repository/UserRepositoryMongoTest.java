package thoughtworks.com.repository;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.junit.Before;
import org.junit.Test;
import thoughtworks.com.domain.*;

import java.util.Date;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserRepositoryMongoTest {
    UserRepositoryImpl userRepository;
    ProductRepositoryImpl productRepository;
    @Before
    public void setUp() throws Exception {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB test = mongoClient.getDB("test");
        userRepository = new UserRepositoryImpl(test);
        productRepository = new ProductRepositoryImpl(test);
    }

    @Test
    public void should_create_user_and_can_get_created_user() throws Exception {
        User kayla = userRepository.createUser(new UserBuilder().name("kayla").build());
        User findedKayla = userRepository.getUserById(kayla.getId());
        assertThat(findedKayla.getName(), is("kayla"));
    }

    @Test
    public void should_create_order_for_user() {
        Product apple = new ProductBuilder().name("apple").description("red apple").build();
        productRepository.createProduct(apple, new Price(1, new Date()));
        User kayla = userRepository.createUser(new UserBuilder().name("kayla").build());
        Order order = userRepository.createOrderForUser(kayla, new Order("beijing", "sofia", "13000000000", asList(new OrderItem(apple.getId(), 2))));
        Order orderOfKayla = userRepository.getUserOrderById(kayla, order.getId());
        assertThat(orderOfKayla.getName(), is("sofia"));
        assertThat(orderOfKayla.getOrderItems().size(), is(1));
        assertThat(orderOfKayla.getOrderItems().get(0).getProductId(), is(apple.getId()));
        assertThat(orderOfKayla.getOrderItems().get(0).getQuantity(), is(2));
    }

    @Test
    public void should_create_payment_for_order(){
        Product apple = new ProductBuilder().name("apple").description("red apple").build();
        productRepository.createProduct(apple, new Price(1, new Date()));
        User kayla = userRepository.createUser(new UserBuilder().name("kayla").build());
        Order orderOfKayla = userRepository.createOrderForUser(kayla, new Order("beijing", "sofia", "13000000000", asList(new OrderItem(apple.getId(), 2))));
        userRepository.createPaymentForUserOrder(orderOfKayla, new Payment("CASH", 100));
        Payment orderPayment = userRepository.getOrderPayment(orderOfKayla);
        assertThat(orderPayment.getAmount(), is(100.0));
        assertThat(orderPayment.getPayType(), is("CASH"));
    }
}
