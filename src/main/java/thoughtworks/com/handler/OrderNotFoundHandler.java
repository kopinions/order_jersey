package thoughtworks.com.handler;

import thoughtworks.com.exception.OrderNotFound;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class OrderNotFoundHandler implements ExceptionMapper<OrderNotFound> {
    @Override
    public Response toResponse(OrderNotFound exception) {
        return Response.status(404).build();
    }
}
