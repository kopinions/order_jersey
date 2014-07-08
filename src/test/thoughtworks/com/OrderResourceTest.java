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
import thoughtworks.com.domain.OrderItem;
import thoughtworks.com.domain.User;
import thoughtworks.com.exception.OrderNotFound;
import thoughtworks.com.exception.UserNotFound;
import thoughtworks.com.repository.UserRepository;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderResourceTest extends JerseyTest {
    @Mock
    UserRepository mockUserRepository;

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    ArgumentCaptor<Order> orderArgumentCaptor;

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
        when(mockUserRepository.getUserById(eq(1))).thenReturn(new User(1, "kayla"));
        when(mockUserRepository.createOrderForUser(any(User.class), any(Order.class))).thenReturn(2);

        Map order = new HashMap<>();
        order.put("address", "beijing");
        order.put("name", "kayla");
        order.put("phone", "13212344321");

        List orderItems = new ArrayList<>();
        Map orderItem = new HashMap<>();
        orderItem.put("productId", 2);
        orderItem.put("quantity", 2);
        orderItems.add(orderItem);
        order.put("orderItems", orderItems);

        Response response = target("/users/1/orders").request().post(Entity.entity(order, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation().toString(), endsWith("/users/1/orders/2"));

        verify(mockUserRepository).createOrderForUser(userArgumentCaptor.capture(), orderArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue().getName(), is("kayla"));

        assertThat(orderArgumentCaptor.getValue().getAddress(), is("beijing"));
        assertThat(orderArgumentCaptor.getValue().getName(), is("kayla"));
        assertThat(orderArgumentCaptor.getValue().getPhone(), is("13212344321"));

        Order orderCaptored = orderArgumentCaptor.getValue();
        assertThat(orderCaptored.getOrderItems().size(), is(1));
        OrderItem orderItemCaptored = (OrderItem) orderCaptored.getOrderItems().get(0);
        assertThat(orderItemCaptored.getQuantity(), is(2));
    }
}
