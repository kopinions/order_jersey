package thoughtworks.com.domain;

import org.bson.types.ObjectId;

public class ProductBuilder {
    private Product product;

    public ProductBuilder() {
        this.product = new Product();
    }

    public ProductBuilder name(String productName) {
        this.product.name = productName;
        return this;
    }

    public ProductBuilder description(String description) {
        this.product.description = description;
        return this;
    }

    public ProductBuilder id(ObjectId id) {
        this.product.id = id;
        return this;
    }


    public ProductBuilder currentPrice(Price currentPrice) {
        this.product.currentPrice = currentPrice;
        return this;
    }

    public Product build() {
        return this.product;
    }
}