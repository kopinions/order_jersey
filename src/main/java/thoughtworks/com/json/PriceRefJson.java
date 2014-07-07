package thoughtworks.com.json;

import thoughtworks.com.domain.Price;

import java.net.URI;

public class PriceRefJson {
    private final Price price;
    private URI uri;

    public PriceRefJson(Price price, URI uri) {

        this.price = price;
        this.uri = uri;
    }

    public double getAmount() {
        return price.getAmount();
    }

    public String getEffectDate() {
        return price.getEffectDate().toString();
    }

    public URI getUri() {
        return URI.create(uri.toString() + "/prices/" + price.getId());
    }
}
