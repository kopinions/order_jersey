package thoughtworks.com.json;

import thoughtworks.com.domain.Product;

public class ProductJson {
    private Product product;

    public ProductJson(Product product) {
        this.product = product;
    }

    public String getName() {
        return product.getName();
    }
}
