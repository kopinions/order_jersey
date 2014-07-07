package thoughtworks.com;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/products")
public class ProductResource {

    @GET
    @Path("{id}")
    public String getProduct(@PathParam("id") int id) {
        return "true";
    }
}
