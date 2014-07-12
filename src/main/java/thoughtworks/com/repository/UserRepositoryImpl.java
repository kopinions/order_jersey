package thoughtworks.com.repository;

import com.mongodb.*;
import org.apache.ibatis.annotations.Param;
import org.bson.types.ObjectId;
import thoughtworks.com.domain.*;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class UserRepositoryImpl implements UserRepository {
    private DB db;

    public UserRepositoryImpl(DB test) {

        db = test;
    }

    @Override
    public User getUserById(ObjectId userId) {
        DBObject one = db.getCollection("users").findOne(new BasicDBObject("_id", userId));
        Map map = one.toMap();
        User user = new UserBuilder().id(new ObjectId(map.get("_id").toString())).name(map.get("name").toString()).build();
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
        List<Map> orderItems = (List) map.get("orderItems");
        List<OrderItem> orderItemsList = orderItems.stream().map(item -> new OrderItem(new ObjectId(item.get("productId").toString()), Integer.valueOf(item.get("quantity").toString()))).collect(toList());
        Order order = new Order(new ObjectId(map.get("_id").toString()), map.get("address").toString(), map.get("name").toString(), map.get("phone").toString(), orderItemsList);
        return order;
    }


    @Override
    public Order createOrderForUser(User user, Order order) {
        ObjectId orderId = new ObjectId();
        BasicDBList orderItems = new BasicDBList();
        order.getOrderItems().stream().forEach(item -> orderItems.add(new BasicDBObject("productId", item.getProductId()).append("_id", new ObjectId()).append("quantity", item.getQuantity())));
        DBObject orderDoc = new BasicDBObjectBuilder()
                .add("_id", orderId)
                .add("name", order.getName())
                .add("address", order.getAddress())
                .add("phone", order.getPhone())
                .add("userId", user.getId())
                .add("orderItems", orderItems)
                .get();
        db.getCollection("orders").insert(orderDoc);
        order.setId(orderId);
        return order;
    }


    @Override
    public Payment getOrderPayment(@Param("order") Order order) {

        Map orderDoc = db.getCollection("orders").findOne(new BasicDBObject("_id", order.getId())).toMap();

        Map payment = (Map) orderDoc.get("payment");
        return new Payment(payment.get("payType").toString(), Double.valueOf(payment.get("amount").toString()));
    }

    @Override
    public Payment createPaymentForUserOrder(Order order, Payment payment) {

        DBCollection orders = db.getCollection("orders");
        DBObject orderDoc = orders.findOne(new BasicDBObject("_id", order.getId()));
        orderDoc.put("payment", new BasicDBObject("payType", payment.getPayType()).append("amount", payment.getAmount()));
        orders.findAndModify(new BasicDBObject("_id", order.getId()), orderDoc);
        return payment;
    }


}
