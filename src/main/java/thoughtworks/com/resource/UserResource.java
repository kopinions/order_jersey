package thoughtworks.com.resource;

import thoughtworks.com.domain.User;
import thoughtworks.com.repository.UserRepository;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/users")
public class UserResource {
    @Inject
    UserRepository userRepository;

    @Path("{userId}/orders")
    public OrderResource getOrders(@PathParam("userId") int userId) {
        User user = userRepository.getUserById(userId);
        return new OrderResource(user, userRepository);
    }

}
