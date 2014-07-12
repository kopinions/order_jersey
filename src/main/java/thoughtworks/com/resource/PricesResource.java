package thoughtworks.com.resource;

import org.bson.types.ObjectId;
import thoughtworks.com.domain.Price;
import thoughtworks.com.domain.Product;
import thoughtworks.com.json.PriceJson;
import thoughtworks.com.repository.ProductRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class PricesResource {

    private final Product product;
    private final ProductRepository productRepository;

    public PricesResource(Product product, ProductRepository productRepository) {

        this.product = product;
        this.productRepository = productRepository;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PriceJson getPrice(@PathParam("id") String id, @Context UriInfo uriInfo) {
        Price price = productRepository.getProductPriceById(product, new ObjectId(id));
        return new PriceJson(price, uriInfo);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProductPrice(@Context UriInfo uriInfo, Map price) {
        double amount = Double.valueOf(price.get("amount").toString());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date effectDate;

        try {
            effectDate = format.parse(price.get("effectDate").toString());
        } catch (ParseException e) {
            return Response.status(400).build();
        }
        Price createdPrice = productRepository.createProductPrice(product, new Price(amount, effectDate));
        return Response.created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(createdPrice.getId())).build()).build();
    }
}
