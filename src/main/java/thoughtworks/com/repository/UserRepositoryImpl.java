package thoughtworks.com.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBObject;
import org.apache.ibatis.annotations.Param;
import org.bson.types.ObjectId;
import thoughtworks.com.domain.Order;
import thoughtworks.com.domain.Payment;
import thoughtworks.com.domain.User;

import java.util.Map;

import static java.util.Arrays.asList;

public class UserRepositoryImpl implements UserRepository {
    private DB db;

    public UserRepositoryImpl(DB test) {

        db = test;
    }

    @Override
    public User getUserById(ObjectId userId) {
        DBObject one = db.getCollection("users").findOne(new BasicDBObject("_id", userId));
        Map map = one.toMap();
        User user = new User(new ObjectId(map.get("_id").toString()),map.get("name").toString());
        return user;
    }

    @Override
    public User createUser(User user) {
        ObjectId userId = new ObjectId();
        DBObject userDoc = new BasicDBObjectBuilder()
                .add("_id", userId)
                .add("name", user.getName())
                .get();
        db.getCollection("users").insert(userDoc);
        user.setId(userId);
        return user;
    }

    @Override
    public Order getUserOrderById(User user, ObjectId orderId) {
        DBObject orderDoc = db.getCollection("orders").findOne(new BasicDBObject("_id", orderId).append("userId", user.getId()));
        Map map = orderDoc.toMap();
        Order order = new Order(new ObjectId(map.get("_id").toString()), map.get("address").toString(), map.get("name").toString(), map.get("phone").toString(), asList());
        return order;
    }


    @Override
    public Order createOrderForUser(User user, Order order) {
        ObjectId orderId = new ObjectId();
        DBObject orderDoc = new BasicDBObjectBuilder()
                .add("_id", orderId)
                .add("name", order.getName())
                .add("address", order.getAddress())
                .add("phone", order.getPhone())
                .add("userId", user.getId())
                .get();
        db.getCollection("orders").insert(orderDoc);
        order.setId(orderId);
        return order;
    }


    @Override
    public Payment getOrderPayment(@Param("order") Order order) {
        return null;
    }

    @Override
    public int createPaymentForUserOrder(@Param("order") Order order, @Param("payment") Payment payment) {
        return 0;
    }
}
