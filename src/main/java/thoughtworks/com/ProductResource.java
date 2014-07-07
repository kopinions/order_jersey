package thoughtworks.com;

import thoughtworks.com.domain.Product;
import thoughtworks.com.json.ProductJson;
import thoughtworks.com.repository.ProductRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/products")
public class ProductResource {

    @Inject
    ProductRepository productRepository;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProductJson getProduct(@PathParam("id") int id, @Context UriInfo uriInfo) {
        Product product = productRepository.getProductById(id);
        return new ProductJson(product, uriInfo);
    }
}