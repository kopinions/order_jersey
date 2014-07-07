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
import thoughtworks.com.exception.OrderNotFound;
import thoughtworks.com.exception.UserNotFound;
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
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderResourceTest extends JerseyTest {
    @Mock
    UserRepository mockUserRepository;

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    @Override
    protected Application configure() {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.packages("thoughtworks.com");
        resourceConfig.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(mockUserRepository).to(UserRepository.class);
            }
        });
        return resourceConfig;
    }

    @Test
    public void should_get_order() {
        Response response = target("/users/1/orders/1").request().get();
        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void should_return_404_when_not_find_user() {
        when(mockUserRepository.getUserById(eq(2))).thenThrow(UserNotFound.class);
        Response response = target("/users/2/orders/1").request().get();

        assertThat(response.getStatus(), is(404));
    }


    @Test
    public void should_return_404_when_user_order_not_found() {
        when(mockUserRepository.getUserOrderById(eq(1))).thenThrow(OrderNotFound.class);
        Response response = target("/users/2/orders/1").request().get();

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_create_order_for_user() {
        when(mockUserRepository.createOrderForUser(any(User.class), any(Order.class))).thenReturn(2);

        Map order = new HashMap<>();
        order.put("address", "beijing");
        order.put("name", "kayla");
        order.put("phone", "13212344321");

        Response response = target("/users/1/orders").request().post(Entity.entity(order, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation().toString(), endsWith("/users/1/orders/2"));
    }
}
