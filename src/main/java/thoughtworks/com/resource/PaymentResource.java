package thoughtworks.com.resource;

import thoughtworks.com.domain.Order;
import thoughtworks.com.domain.Payment;
import thoughtworks.com.domain.User;
import thoughtworks.com.json.PaymentJson;
import thoughtworks.com.repository.UserRepository;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Map;

public class PaymentResource {


    private final User user;
    private final Order order;
    private final UserRepository userRepository;

    public PaymentResource(User user, Order order, UserRepository userRepository) {

        this.user = user;
        this.order = order;
        this.userRepository = userRepository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PaymentJson getPayment(@Context UriInfo uriInfo) {
        Payment payment = userRepository.getOrderPayment(order);
        return new PaymentJson(uriInfo, payment);
    }

    @POST
    public Response createPayment(@Context UriInfo uriInfo, Map payment) {
        String payType = payment.get("type").toString();
        double amount = Double.valueOf(payment.get("amount").toString());
        userRepository.createPaymentForUserOrder(order, new Payment(payType, amount));
        return Response.created(uriInfo.getAbsolutePathBuilder().build()).build();
    }
    
}
