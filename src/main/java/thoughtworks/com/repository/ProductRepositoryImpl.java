package thoughtworks.com.repository;

import com.mongodb.*;
import org.bson.types.ObjectId;
import thoughtworks.com.domain.Price;
import thoughtworks.com.domain.Product;
import thoughtworks.com.domain.ProductBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

public class ProductRepositoryImpl implements ProductRepository {
    private DB db;

    public ProductRepositoryImpl(DB db) {
        this.db = db;
    }

    @Override
    public Product getProductById(ObjectId productId) {
        DBObject productDoc = db.getCollection("products").findOne(new BasicDBObject("_id", productId));
        Map currentPriceDoc = (Map) productDoc.get("currentPrice");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:MM:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Price currentPrice;
        try {
            currentPrice = new Price(new ObjectId(currentPriceDoc.get("_id").toString()),Double.valueOf(currentPriceDoc.get("amount").toString()), dateFormat.parse(currentPriceDoc.get("effectDate").toString()));
        } catch (ParseException e) {
            return null;
        }
        return new ProductBuilder()
                .id(new ObjectId(productDoc.get("_id").toString()))
                .name(productDoc.get("name").toString())
                .description(productDoc.get("description").toString())
                .currentPrice(currentPrice).build();
    }

    @Override
    public Product createProduct(Product product) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        ObjectId productId = new ObjectId();
        ObjectId priceId = new ObjectId();

        DBObject priceDoc = new BasicDBObjectBuilder()
                .add("_id", priceId)
                .add("amount", product.getCurrentPrice().getAmount())
                .add("effectDate", dateFormat.format(product.getCurrentPrice().getEffectDate())).get();

        DBObject productDoc = new BasicDBObjectBuilder()
                .add("_id", productId)
                .add("name", product.getName())
                .add("description", product.getDescription())
                .add("currentPrice", priceDoc)
                .get();

        product.setId(productId);

        db.getCollection("products").insert(productDoc);

        return product;
    }

    @Override
    public Price getProductPriceById(Product product,  ObjectId priceId) {
        DBObject findPrice = db.getCollection("prices").findOne(new BasicDBObject("productId", product.getId()).append("_id", priceId));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date effectDate = null;
        try {
            effectDate = dateFormat.parse(findPrice.get("effectDate").toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Price(priceId, Double.valueOf(findPrice.get("amount").toString()), effectDate);
    }

    @Override
    public Price createProductPrice(Product product, Price price) {
        ObjectId priceId = new ObjectId();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formatedDateString = dateFormat.format(price.getEffectDate());
        DBObject priceDoc = new BasicDBObjectBuilder()
                .add("productId", product.getId())
                .add("_id", priceId)
                .add("amount", price.getAmount())
                .add("effectDate", formatedDateString)
                .get();
        DBObject findedProduct = db.getCollection("products").findOne(new BasicDBObject("_id", product.getId()));
        BasicDBList historyPrices = (BasicDBList) findedProduct.get("historyPrices");
        if (historyPrices == null) {
            historyPrices = new BasicDBList();
        }
        historyPrices.add(new DBRef(db, "prices", priceId));

        if (price.getEffectDate().compareTo(product.getCurrentPrice().getEffectDate()) > 0) {
            historyPrices.add(new DBRef(db, "prices", product.getCurrentPrice().getId()));
            findedProduct.put("currentPrice", priceDoc);
        }

        findedProduct.put("historyPrices", historyPrices);
        db.getCollection("prices").insert(priceDoc);
        db.getCollection("products").findAndModify(new BasicDBObject("_id", product.getId()), findedProduct);
        price.setId(priceId);
        return price;
    }
}
