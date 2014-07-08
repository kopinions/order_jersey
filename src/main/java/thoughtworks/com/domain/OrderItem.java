package thoughtworks.com.domain;

public class OrderItem {
    private int productId;
    private int quantity;

    public OrderItem() {
    }

    public OrderItem(int productId, int quantity) {

        this.productId = productId;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
