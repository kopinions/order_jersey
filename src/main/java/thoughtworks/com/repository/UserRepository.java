package thoughtworks.com.repository;

import org.apache.ibatis.annotations.Param;
import thoughtworks.com.domain.Order;
import thoughtworks.com.domain.Payment;
import thoughtworks.com.domain.User;

public interface UserRepository {
    public User getUserById(@Param("userId") int userId);

    int createUser(@Param("user") User user);

    Order getUserOrderById(@Param("user") User user, @Param("orderId") int orderId);

    int createOrderForUser(@Param("user") User user, @Param("order") Order order);

    Payment getOrderPayment(@Param("order") Order order);

    int createPaymentForUserOrder(@Param("order") Order order, @Param("payment") Payment payment);

}
