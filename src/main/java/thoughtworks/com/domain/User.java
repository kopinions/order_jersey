package thoughtworks.com.domain;

import org.bson.types.ObjectId;

public class User {
    ObjectId id;
    String name;

    public User(ObjectId id, String name) {

        this.id = id;
        this.name = name;
    }

    public User() {

    }

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public org.bson.types.ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}
