package thoughtworks.com.handler;

import thoughtworks.com.exception.UserNotFound;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserNotFoundHandler implements ExceptionMapper<UserNotFound> {
    @Override
    public Response toResponse(UserNotFound exception) {
        return Response.status(404).build();
    }
}
