package thoughtworks.com;

import thoughtworks.com.domain.Order;
import thoughtworks.com.domain.Payment;
import thoughtworks.com.domain.User;
import thoughtworks.com.repository.UserRepository;

import javax.ws.rs.GET;

public class PaymentResource {


    private final User user;
    private final Order order;
    private final UserRepository userRepository;

    public PaymentResource(User user, Order order, UserRepository userRepository) {

        this.user = user;
        this.order = order;
        this.userRepository = userRepository;
    }

    @GET
    public String getPayment() {
        Payment payment = userRepository.getUserOrderPayment(user, order);
        return "test";
    }
}
