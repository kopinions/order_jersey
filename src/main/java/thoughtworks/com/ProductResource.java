package thoughtworks.com;

import thoughtworks.com.domain.Price;
import thoughtworks.com.domain.Product;
import thoughtworks.com.json.ProductJson;
import thoughtworks.com.repository.ProductRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Map;

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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProduct(@Context UriInfo uriInfo, Map<String, Object> request) {
        String productName = request.get("name").toString();
        String description = request.get("description").toString();

        Map price = (Map) request.get("price");
        double amount = Double.valueOf(price.get("amount").toString());

        int productId = productRepository.createProduct(new Product(productName, description), new Price(amount));
        return Response.created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(productId)).build()).build();
    }

}
