package thoughtworks.com;

import thoughtworks.com.domain.Price;
import thoughtworks.com.domain.Product;
import thoughtworks.com.repository.ProductRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProductPrice(@Context UriInfo uriInfo) {
        int priceId = productRepository.createProductPrice(product, new Price());
        return Response.created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(priceId)).build()).build();
    }
}
