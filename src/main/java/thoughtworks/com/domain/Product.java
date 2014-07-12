package thoughtworks.com.domain;

import org.bson.types.ObjectId;

public class Product {
    ObjectId id;
    String description;
    String name;
    Price currentPrice;

    public Product() {
    }

    public Product(ObjectId id, String name, String description, Price currentPrice) {
        this.setId(id);
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

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}
