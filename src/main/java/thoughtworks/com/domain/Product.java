package thoughtworks.com.domain;

public class Product {
    private int productId;
    private String description;
    private String name;

    public Product(int productId, String productName, String description) {
        this.productId = productId;
        this.description = description;
        this.name = productName;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
