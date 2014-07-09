package thoughtworks.com.repository;

import com.mongodb.*;
import org.apache.ibatis.annotations.Param;
import org.bson.types.ObjectId;
import thoughtworks.com.domain.Price;
import thoughtworks.com.domain.Product;

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
    public Price getProductPriceById(@Param("product") Product product, @Param("priceId") int priceId) {
        return null;
    }

    @Override
    public ObjectId createProductPrice(@Param("product") Product product, @Param("price") Price price) {
        return ObjectId.massageToObjectId(0);
    }
}
