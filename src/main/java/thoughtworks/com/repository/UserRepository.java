package thoughtworks.com.repository;

import thoughtworks.com.domain.User;

public interface UserRepository {
    public User getUserById(int eq);
}
