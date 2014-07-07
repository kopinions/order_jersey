package thoughtworks.com.domain;

public class Product {
    private int productId;
    private String description;
    private String name;
    private Price currentPrice;

    public Product(int productId, String productName, String description) {
        this.productId = productId;
        this.description = description;
        this.name = productName;
    }

    public Product(String productName, String description) {
        name = productName;
        this.description = description;
    }

    public Product(int productId, String name, String description, Price currentPrice) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.currentPrice = currentPrice;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Price getCurrentPrice() {
        return currentPrice;
    }
}
