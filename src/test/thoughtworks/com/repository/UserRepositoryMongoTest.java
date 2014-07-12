package thoughtworks.com.repository;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.junit.Before;
import org.junit.Test;
import thoughtworks.com.domain.Order;
import thoughtworks.com.domain.User;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserRepositoryMongoTest {
    UserRepositoryImpl userRepository;

    @Before
    public void setUp() throws Exception {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB test = mongoClient.getDB("test");
        userRepository = new UserRepositoryImpl(test);
    }

    @Test
    public void should_create_user_and_can_get_created_user() throws Exception {
        User kayla = userRepository.createUser(new User("kayla"));
        User findedKayla = userRepository.getUserById(kayla.getId());
        assertThat(findedKayla.getName(), is("kayla"));
    }

    @Test
    public void should_create_order_for_user(){
        User kayla = userRepository.createUser(new User("kayla"));
        Order order = userRepository.createOrderForUser(kayla, new Order("beijing", "sofia", "13000000000", asList()));
        Order orderOfKayla = userRepository.getUserOrderById(kayla, order.getId());
        assertThat(orderOfKayla.getName(), is("sofia"));
    }
}
