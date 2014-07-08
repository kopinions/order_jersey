package thoughtworks.com.domain;

public class Product {
    private int id;
    private String description;
    private String name;
    private Price currentPrice;

    public Product() {
    }

    public Product(int id, String productName, String description) {
        this.id = id;
        this.description = description;
        this.name = productName;
    }

    public Product(String productName, String description) {
        name = productName;
        this.description = description;
    }

    public Product(int id, String name, String description, Price currentPrice) {
        this.id = id;
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

    public int getId() {
        return id;
    }
}
