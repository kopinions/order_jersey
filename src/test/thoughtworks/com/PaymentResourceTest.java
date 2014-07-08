package thoughtworks.com;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import thoughtworks.com.domain.Order;
import thoughtworks.com.domain.User;
import thoughtworks.com.exception.PaymentNotFound;
import thoughtworks.com.repository.UserRepository;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PaymentResourceTest extends JerseyTest {

    @Mock
    UserRepository userRepository;

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    @Override
    protected Application configure() {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.packages("thoughtworks.com");
        resourceConfig.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(userRepository).to(UserRepository.class);
            }
        });
        return resourceConfig;
    }

    @Test
    public void should_return_200_when_get_payment() {
        Response response = target("/users/1/orders/2/payment").request().get();
        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void should_return_404_when_not_found_payment() {
        when(userRepository.getUserOrderPayment(any(User.class), any(Order.class))).thenThrow(PaymentNotFound.class);
        Response response = target("/users/1/orders/2/payment").request().get();

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_create_payment_for_user_order() {
        when(userRepository.getUserById(eq(1))).thenReturn(new User(1, "kayla"));
        Map payment = new HashMap<>();
        payment.put("type", "CASH");
        Response response = target("/users/1/orders/2/payment").request().post(Entity.entity(payment, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatus(), is(201));

        assertThat(response.getLocation().toString(), endsWith("/users/1/orders/2/payment"));
        verify(userRepository).createPaymentForUserOrder(userArgumentCaptor.capture(), anyObject(), anyObject());
        assertThat(userArgumentCaptor.getValue().getName(), is("kayla"));
    }
}
