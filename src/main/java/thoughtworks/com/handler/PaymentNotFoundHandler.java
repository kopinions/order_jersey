package thoughtworks.com.handler;

import thoughtworks.com.exception.PaymentNotFound;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PaymentNotFoundHandler implements ExceptionMapper<PaymentNotFound> {
    @Override
    public Response toResponse(PaymentNotFound exception) {
        return Response.status(404).build();
    }
}
