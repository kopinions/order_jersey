package thoughtworks.com.json;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class PaymentJson {
    private final UriInfo uriInfo;

    public PaymentJson(UriInfo uriInfo) {

        this.uriInfo = uriInfo;
    }

    public URI getUri() {
        return uriInfo.getAbsolutePathBuilder().build();
    }
}
