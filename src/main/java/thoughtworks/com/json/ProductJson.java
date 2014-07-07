package thoughtworks.com.json;

import thoughtworks.com.domain.Product;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class ProductJson {
    private Product product;
    private UriInfo uriInfo;

    public ProductJson(Product product, UriInfo uriInfo) {
        this.product = product;
        this.uriInfo = uriInfo;
    }

    public String getName() {
        return product.getName();
    }

    public String getDescription() {
        return product.getDescription();
    }

    public URI getUri() {
        return uriInfo.getAbsolutePathBuilder().build();
    }

}
