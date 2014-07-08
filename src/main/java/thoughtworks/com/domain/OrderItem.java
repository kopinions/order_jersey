package thoughtworks.com.domain;

public class OrderItem {
    private final int productId;
    private final int quantity;

    public OrderItem(int productId, int quantity) {

        this.productId = productId;
        this.quantity = quantity;
    }
}
