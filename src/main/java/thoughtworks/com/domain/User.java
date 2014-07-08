package thoughtworks.com.domain;

public class User {
    private int id;
    private String username;

    public User(int id, String username) {

        this.id = id;
        this.username = username;
    }

    public User() {

    }

    public String getName() {
        return username;
    }
}
