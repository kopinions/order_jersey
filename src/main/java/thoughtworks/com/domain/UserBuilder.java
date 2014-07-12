package thoughtworks.com.domain;

import org.bson.types.ObjectId;

public class UserBuilder {
    private User user;
    private ObjectId id;
    private String name;

    public UserBuilder() {
        this.user = new User();
    }

    public UserBuilder id(ObjectId id) {
        this.user.id = id;
        return this;
    }

    public UserBuilder name(String name) {
        this.user.name = name;
        return this;
    }

    public User build() {
        return user;
    }
}