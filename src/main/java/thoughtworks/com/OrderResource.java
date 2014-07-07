package thoughtworks.com;

import thoughtworks.com.domain.Order;
import thoughtworks.com.domain.User;
import thoughtworks.com.repository.UserRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

public class OrderResource {
    private User user;
    private final UserRepository userRepository;

    public OrderResource(User user, UserRepository userRepository) {
        this.user = user;
        this.userRepository = userRepository;
    }

    @GET
    @Path("{orderId}")
    public String getOrder(@PathParam("orderId") int orderId) {
        Order order = userRepository.getUserOrderById(orderId);
        return "test";
    }
}
