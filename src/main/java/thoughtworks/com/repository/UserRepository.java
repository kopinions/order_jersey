package thoughtworks.com.repository;

import org.bson.types.ObjectId;
import thoughtworks.com.domain.Order;
import thoughtworks.com.domain.Payment;
import thoughtworks.com.domain.User;

public interface UserRepository {
    public User getUserById(ObjectId userId);

    User createUser(User user);

    Order getUserOrderById(User user, ObjectId orderId);

    Order createOrderForUser(User user, Order order);

    Payment getOrderPayment(Order order);

    Payment createPaymentForUserOrder(Order order, Payment payment);

}
