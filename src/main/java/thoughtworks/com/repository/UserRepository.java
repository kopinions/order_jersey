package thoughtworks.com.repository;

import org.apache.ibatis.annotations.Param;
import thoughtworks.com.domain.Order;
import thoughtworks.com.domain.Payment;
import thoughtworks.com.domain.User;

public interface UserRepository {
    public User getUserById(@Param("userId") int userId);

    int createUser(@Param("user") User user);

    Order getUserOrderById(User user, int orderId);

    int createOrderForUser(User user, Order order);

    Payment getUserOrderPayment(User user, Order order);

    int createPaymentForUserOrder(User user, Order order, Payment payment);

}
