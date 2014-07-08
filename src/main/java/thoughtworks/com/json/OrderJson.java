package thoughtworks.com.json;

import thoughtworks.com.domain.Order;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class OrderJson {
    private Order order;
    private UriInfo uriInfo;

    public OrderJson(Order order, UriInfo uriInfo) {

        this.order = order;
        this.uriInfo = uriInfo;
    }

    public String getAddress() {
        return order.getAddress();
    }

    public String getName() {
        return order.getName();
    }

    public String getPhone() {
        return order.getPhone();
    }

    public URI getUri() {
        return uriInfo.getAbsolutePathBuilder().build();
    }
}
