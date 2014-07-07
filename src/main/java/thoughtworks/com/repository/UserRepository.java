package thoughtworks.com.repository;

import thoughtworks.com.domain.Order;
import thoughtworks.com.domain.User;

public interface UserRepository {
    public User getUserById(int eq);

    Order getUserOrderById(int eq);

    int createOrderForUser(User user, Order order);
}
