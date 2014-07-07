package thoughtworks.com;

import thoughtworks.com.domain.Product;
import thoughtworks.com.repository.ProductRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/products")
public class ProductResource {

    @Inject
    ProductRepository productRepository;

    @GET
    @Path("{id}")
    public String getProduct(@PathParam("id") int id) {
        Product product = productRepository.getProductById(id);
        return "true";
    }
}
