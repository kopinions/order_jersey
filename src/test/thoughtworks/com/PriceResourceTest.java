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
import thoughtworks.com.domain.Price;
import thoughtworks.com.domain.Product;
import thoughtworks.com.exception.PriceNotFound;
import thoughtworks.com.exception.ProductNotFound;
import thoughtworks.com.repository.ProductRepository;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PriceResourceTest extends JerseyTest {
    @Mock
    ProductRepository productRepository;


    @Captor
    ArgumentCaptor<Product> productArgumentCaptor;

    @Captor
    ArgumentCaptor<Price> priceArgumentCaptor;

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
    public void should_get_200() throws ParseException {
        when(productRepository.getProductById(eq(2))).thenReturn(new Product("name", "description"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        when(productRepository.getProductPriceById(any(Product.class), eq(1))).thenReturn(new Price(100, dateFormat.parse("2014-01-01")));
        Response response = target("/products/2/prices/1").request().get();
        assertThat(response.getStatus(), is(200));

        Map price = response.readEntity(Map.class);
        assertThat(price.get("amount"), is(100.0));
        assertThat(price.get("effectDate"), is(dateFormat.parse("2014-01-01").toString()));
        assertThat(price.get("uri").toString(), endsWith("/products/2/prices/1"));
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
    public void should_create_product_price() throws ParseException {
        when(productRepository.createProductPrice(any(Product.class), any(Price.class))).thenReturn(2);
        when(productRepository.getProductById(eq(1))).thenReturn(new Product("name", "description"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        HashMap<Object, Object> price = new HashMap<>();
        price.put("amount", 1);
        price.put("effectDate", "2014-01-01");
        Response response = target("/products/1/prices").request().post(Entity.entity(price, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation().toString(), endsWith("/products/1/prices/2"));

        verify(productRepository).createProductPrice(productArgumentCaptor.capture(), priceArgumentCaptor.capture());
        assertThat(productArgumentCaptor.getValue().getName(), is("name"));

        assertThat(priceArgumentCaptor.getValue().getAmount(), is(1.0));
        assertThat(priceArgumentCaptor.getValue().getEffectDate(), is(dateFormat.parse("2014-01-01")));
    }
}
