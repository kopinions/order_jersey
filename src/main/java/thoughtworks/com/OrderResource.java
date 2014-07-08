package thoughtworks.com;

import thoughtworks.com.domain.Order;
import thoughtworks.com.domain.OrderItem;
import thoughtworks.com.domain.User;
import thoughtworks.com.repository.UserRepository;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

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

    @POST
    public Response createOrder(@Context UriInfo uriInfo, Map order) {
        String address = order.get("address").toString();
        String name = order.get("name").toString();
        String phone = order.get("phone").toString();

        List orderItems = (List) order.get("orderItems");
        List<OrderItem> orderItemsCreated = (List<OrderItem>) orderItems.stream().map(item -> {
            Map map = (Map) item;
            return new OrderItem(Integer.valueOf(map.get("productId").toString()), Integer.valueOf(map.get("quantity").toString()));
        }).collect(toList());
        int orderId = userRepository.createOrderForUser(user, new Order(address, name, phone, orderItemsCreated));
        return Response.created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(orderId)).build()).build();
    }
}
