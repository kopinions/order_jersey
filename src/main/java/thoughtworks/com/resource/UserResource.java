package thoughtworks.com.resource;

import org.bson.types.ObjectId;
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
    public OrderResource getOrders(@PathParam("userId") String userId) {
        User user = userRepository.getUserById(new ObjectId(userId));
        return new OrderResource(user, userRepository);
    }

}
