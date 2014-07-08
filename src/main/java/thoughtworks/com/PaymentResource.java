package thoughtworks.com;

import thoughtworks.com.domain.Order;
import thoughtworks.com.domain.Payment;
import thoughtworks.com.domain.User;
import thoughtworks.com.repository.UserRepository;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
    public String getPayment() {
        Payment payment = userRepository.getUserOrderPayment(user, order);
        return "test";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPayment(@Context UriInfo uriInfo, Map payment) {
        String payType = payment.get("type").toString();
        int paymentResult = userRepository.createPaymentForUserOrder(user, order, new Payment(payType));
        return Response.created(uriInfo.getAbsolutePathBuilder().build()).build();
    }
    
}
