package thoughtworks.com;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

public class PricesResource {

    @GET
    @Path("{id}")
    public double getPrice(@PathParam("id") int id) {
        return 0;
    }
}
