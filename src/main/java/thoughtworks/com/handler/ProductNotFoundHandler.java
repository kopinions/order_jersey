package thoughtworks.com.handler;

import thoughtworks.com.exception.ProductNotFound;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ProductNotFoundHandler implements ExceptionMapper<ProductNotFound> {
    @Override
    public Response toResponse(ProductNotFound exception) {
        return Response.status(404).build();
    }
}
