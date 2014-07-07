package thoughtworks.com.handler;

import thoughtworks.com.exception.PriceNotFound;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PriceNotFoundHandler implements ExceptionMapper<PriceNotFound> {
    @Override
    public Response toResponse(PriceNotFound exception) {
        return Response.status(404).build();
    }
}
