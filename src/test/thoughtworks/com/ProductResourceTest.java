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
import thoughtworks.com.exception.ProductNotFound;
import thoughtworks.com.repository.ProductRepository;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductResourceTest extends JerseyTest {

    @Mock
    ProductRepository mockProductRepository;

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
                bind(mockProductRepository).to(ProductRepository.class);
            }
        });
        return resourceConfig;
    }

    @Test
    public void should_return_200_when_get() {
        when(mockProductRepository.getProductById(eq(1))).thenReturn(new Product(2, "name", "description", new Price(100, new Date())));
        Response response = target("/products/1").request().accept(MediaType.APPLICATION_JSON_TYPE).get();
        assertThat(response.getStatus(), is(200));

        Map product = response.readEntity(Map.class);

        assertThat(product.get("name"), is("name"));
        assertThat(product.get("description"), is("description"));
        assertThat(product.get("uri").toString(), endsWith("/products/1"));

        Map price = (Map) product.get("price");
        assertThat(price.get("amount"), is(100.0));
        assertThat(price.containsKey("effectDate"), is(true));
    }

    @Test
    public void should_return_404_when_not_found_product() {
        when(mockProductRepository.getProductById(eq(999))).thenThrow(ProductNotFound.class);
        Response response = target("/products/999").request().get();
        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_create_product() throws ParseException {
        when(mockProductRepository.createProduct(anyObject(), anyObject())).thenReturn(2);
        Map<String, Object> product = new HashMap<>();
        product.put("name", "productName");
        product.put("description", "description");

        Map<String, Object> price = new HashMap<>();
        price.put("amount", 1.0);
        price.put("effectDate", "2014-01-01");
        product.put("price", price);

        Response response = target("/products").request().post(Entity.entity(product, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(201));

        assertThat(response.getLocation().toString(), endsWith("/products/2"));
        verify(mockProductRepository).createProduct(productArgumentCaptor.capture(), priceArgumentCaptor.capture());
        assertThat(productArgumentCaptor.getValue().getName(), is("productName"));
        assertThat(productArgumentCaptor.getValue().getDescription(), is("description"));

        assertThat(priceArgumentCaptor.getValue().getAmount(), is(1.0));
        assertThat(priceArgumentCaptor.getValue().getEffectDate(), is(new SimpleDateFormat("yyyy-MM-dd").parse("2014-01-01")));
    }
}
