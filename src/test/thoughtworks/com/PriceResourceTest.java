package thoughtworks.com;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import thoughtworks.com.domain.Product;
import thoughtworks.com.exception.PriceNotFound;
import thoughtworks.com.exception.ProductNotFound;
import thoughtworks.com.repository.ProductRepository;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PriceResourceTest extends JerseyTest {
    @Mock
    ProductRepository productRepository;

    @Override
    protected Application configure() {

        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.packages("thoughtworks.com");
        resourceConfig.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(productRepository).to(ProductRepository.class);
            }
        });
        return resourceConfig;
    }


    @Test
    public void should_get_200(){
        Response response = target("/products/2/prices/1").request().get();
        assertThat(response.getStatus(), is(200));
    }


    @Test
    public void should_return_404_when_not_found_product() {
        when(productRepository.getProductById(eq(2))).thenThrow(ProductNotFound.class);
        Response response = target("/products/2/prices/1").request().get();
        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_return_404_when_not_found_price() {
        when(productRepository.getProductPriceById(any(Product.class), eq(1))).thenThrow(PriceNotFound.class);
        Response response = target("/products/2/prices/1").request().get();
        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_create_product_price() {
        HashMap<Object, Object> price = new HashMap<>();
        price.put("amount", 1);
        price.put("effectDate", "2014-01-01");
        Response response = target("/products/1/prices").request().post(Entity.entity(price, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(201));
    }
}
