package thoughtworks.com.json;

import thoughtworks.com.domain.Order;

public class OrderJson {
    private Order order;

    public OrderJson(Order order) {

        this.order = order;
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
}
