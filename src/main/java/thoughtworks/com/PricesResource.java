package thoughtworks.com;

import thoughtworks.com.domain.Price;
import thoughtworks.com.domain.Product;
import thoughtworks.com.repository.ProductRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

public class PricesResource {

    private final Product product;
    private final ProductRepository productRepository;

    public PricesResource(Product product, ProductRepository productRepository) {

        this.product = product;
        this.productRepository = productRepository;
    }

    @GET
    @Path("{id}")
    public double getPrice(@PathParam("id") int id) {
        Price price = productRepository.getProductPriceById(product, id);
        return 0;
    }
}
