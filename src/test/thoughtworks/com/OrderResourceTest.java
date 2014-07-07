package thoughtworks.com;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import thoughtworks.com.exception.UserNotFound;
import thoughtworks.com.repository.UserRepository;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderResourceTest extends JerseyTest {
    @Mock
    UserRepository mockUserRepository;
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
}
