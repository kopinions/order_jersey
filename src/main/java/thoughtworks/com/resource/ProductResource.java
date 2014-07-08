package thoughtworks.com.resource;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Path("/products")
public class ProductResource {

    @Inject
    ProductRepository productRepository;

    @GET
    @Path("{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProductJson getProduct(@PathParam("productId") int id, @Context UriInfo uriInfo) {
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

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date effectDate;
        try {
            effectDate = formatter.parse(price.get("effectDate").toString());
        } catch (ParseException e) {
            return Response.status(400).build();
        }

        int productId = productRepository.createProduct(new Product(productName, description), new Price(amount, effectDate));
        return Response.created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(productId)).build()).build();
    }

    @Path("{productId}/prices")
    public PricesResource getProductPrice(@PathParam("productId") int id) {
        Product product = productRepository.getProductById(id);
        return new PricesResource(product, productRepository);
    }
}
