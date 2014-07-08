package thoughtworks.com;

import javax.ws.rs.GET;

public class PaymentResource {


    @GET
    public String getPayment() {
        return "test";
    }
}
