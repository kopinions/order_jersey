package thoughtworks.com.domain;

import org.bson.types.ObjectId;

import java.util.List;

public class Order {
    private ObjectId id;
    private String address;
    private String name;
    private String phone;
    private List<OrderItem> orderItems;

    public Order() {
    }

    public Order(String address, String name, String phone, List orderItems) {
        this.address = address;
        this.name = name;
        this.phone = phone;
        this.orderItems = orderItems;
    }

    public Order(ObjectId id, String address, String name, String phone, List<OrderItem> orderItems) {

        this.id = id;
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

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}
