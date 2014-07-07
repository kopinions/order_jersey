package thoughtworks.com.repository;

import thoughtworks.com.OrderResource;

import javax.ws.rs.Path;

@Path("/users")
public class UserResource {
    @Path("{userId}/orders")
    public OrderResource getOrders() {
        return new OrderResource();
    }

}
