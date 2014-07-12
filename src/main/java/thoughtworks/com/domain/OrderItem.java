package thoughtworks.com.domain;

import org.bson.types.ObjectId;

public class OrderItem {
    private ObjectId id;
    private ObjectId productId;
    private int quantity;

    public OrderItem(ObjectId id, ObjectId productId, Integer quantity) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
    }

    public OrderItem(ObjectId productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public ObjectId getProductId() {
        return productId;
    }
}
