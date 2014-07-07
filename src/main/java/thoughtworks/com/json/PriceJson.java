package thoughtworks.com.json;

import thoughtworks.com.domain.Price;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class PriceJson {
    private final Price price;
    private UriInfo uriInfo;

    public PriceJson(Price price, UriInfo uriInfo) {

        this.price = price;
        this.uriInfo = uriInfo;
    }

    public double getAmount() {
        return price.getAmount();
    }

    public String getEffectDate() {
        return String.valueOf(price.getEffectDate());
    }

    public URI getUri() {
        return uriInfo.getAbsolutePathBuilder().build();
    }
}
