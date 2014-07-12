package thoughtworks.com.repository;

import com.mongodb.*;
import org.bson.types.ObjectId;
import thoughtworks.com.domain.Price;
import thoughtworks.com.domain.Product;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ProductRepositoryImpl implements ProductRepository {
    private DB db;

    public ProductRepositoryImpl(DB db) {
        this.db = db;
    }

    @Override
    public Product getProductById(ObjectId productId) {
        DBObject productDoc = db.getCollection("products").findOne(new BasicDBObject("_id", productId));
        return new Product(new ObjectId(productDoc.get("_id").toString()), productDoc.get("name").toString(), productDoc.get("description").toString());
    }

    @Override
    public int createProduct(Product product, Price price) {
        BasicDBObjectBuilder builder = new BasicDBObjectBuilder();
        ObjectId productId = new ObjectId();
        ObjectId priceId = new ObjectId();
        DBObject priceDoc = new BasicDBObjectBuilder()
                .add("_id", priceId)
                .add("amount", price.getAmount())
                .add("effectDate", price.getEffectDate()).get();
        DBObject productDoc = builder
                .add("_id", productId)
                .add("name", product.getName())
                .add("description", product.getDescription())
                .add("currentPrice", priceDoc)
                .get();
        product.setId(productId);
        try {
            db.getCollection("products").insert(productDoc);
        } catch (MongoException e) {
            return 0;
        }
        return 1;
    }

    @Override
    public Price getProductPriceById(Product product,  ObjectId priceId) {
        DBObject findPrice = db.getCollection("prices").findOne(new BasicDBObject("productId", product.getId()).append("_id", priceId));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
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
    public int createProductPrice(Product product,Price price) {
        ObjectId priceId = new ObjectId();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formatedDateString = dateFormat.format(price.getEffectDate());
        DBObject priceDoc = new BasicDBObjectBuilder()
                .add("productId", product.getId())
                .add("_id", priceId)
                .add("amount", price.getAmount())
                .add("effectDate", formatedDateString).get();
        DBObject findedProduct = db.getCollection("products").findOne(new BasicDBObject("_id", product.getId()));
        BasicDBList historyPrices = (BasicDBList) findedProduct.get("historyPrices");
        if (historyPrices == null) {
            historyPrices = new BasicDBList();
        }
        historyPrices.add(priceDoc);
        findedProduct.put("historyPrices", historyPrices);

        db.getCollection("products").findAndModify(new BasicDBObject("_id", product.getId()), findedProduct);
        db.getCollection("prices").insert(priceDoc);
        price.setId(priceId);
        return 1;
    }
}
