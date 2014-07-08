package thoughtworks.com.repository;

import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import thoughtworks.com.domain.Order;
import thoughtworks.com.domain.User;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserRepositoryTest {
    SqlSession sqlSession;

    @Before
    public void setUp() throws Exception {
        MyBatisSessionFactory myBatisSessionFactory = new MyBatisSessionFactory();
        sqlSession = myBatisSessionFactory.getSqlSession();
    }

    @After
    public void tearDown() throws Exception {
        sqlSession.rollback();
    }

    @Test
    public void should_create_and_get_user_by_id() {
        UserRepository userRepository = sqlSession.getMapper(UserRepository.class);
        User kayla = new User("kayla");
        int effectRow = userRepository.createUser(kayla);
        assertThat(effectRow > 0, is(true));

        User userGot = userRepository.getUserById(kayla.getId());

        assertThat(userGot.getName(), is("kayla"));
    }

    @Test
    public void should_create_order_for_user_then_get_order() {
        UserRepository userRepository = sqlSession.getMapper(UserRepository.class);
        User kayla = new User("kayla");
        userRepository.createUser(kayla);

        Order orderForKayla = new Order("beijing", "name", "13200000000", asList());
        userRepository.createOrderForUser(kayla, orderForKayla);

        assertThat(orderForKayla.getId()>0, is(true));
    }
}
