package thoughtworks.com;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

public class OrderResource {
    @GET
    @Path("{orderId}")
    public String getOrder() {
        return "test";
    }
}
