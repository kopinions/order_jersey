package thoughtworks.com.json;

import thoughtworks.com.domain.Payment;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class PaymentJson {
    private final UriInfo uriInfo;
    private Payment payment;

    public PaymentJson(UriInfo uriInfo, Payment payment) {

        this.uriInfo = uriInfo;
        this.payment = payment;
    }

    public URI getUri() {
        return uriInfo.getAbsolutePathBuilder().build();
    }

    public double getAmount() {
        return payment.getAmount();
    }

    public String getType() {
        return payment.getPayType();
    }
}
