package tukano.api;

import tukano.api.rest.RestUsers;

import java.util.List;

public class RestUser implements RestUsers {
    @Override
    public String createUser(User user) {
        return null;
    }

    @Override
    public User getUser(String userId, String pwd) {
        return null;
    }

    @Override
    public User updateUser(String userId, String pwd, User user) {
        return null;
    }

    @Override
    public User deleteUser(String userId, String pwd) {
        return null;
    }

    @Override
    public List<User> searchUsers(String pattern) {
        return null;
    }
}
