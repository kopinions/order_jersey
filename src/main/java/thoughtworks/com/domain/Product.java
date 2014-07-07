package thoughtworks.com.domain;

import java.net.URI;

public class Product {
    private String description;
    private String name;

    public Product(String productName, String description) {
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
