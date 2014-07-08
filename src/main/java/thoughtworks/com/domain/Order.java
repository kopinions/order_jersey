package thoughtworks.com.domain;

import java.util.List;

public class Order {
    private String address;
    private String name;
    private String phone;
    private List orderItems;

    public Order(String address, String name, String phone, List orderItems) {

        this.address = address;
        this.name = name;
        this.phone = phone;
        this.orderItems = orderItems;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public List getOrderItems() {
        return orderItems;
    }
}
