package facade;

import broker.JPABrokerBase;
import broker.JPAUserBroker;
import models.User;

import java.sql.SQLException;
import java.util.List;

public class JPAUserFacade extends JPAFacade {
    public List<User> getAllUsers() {
        JPABrokerBase<User> broker = new JPAUserBroker();
        try {
            return broker.getAll();
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserById(String id) {
        JPABrokerBase<User> broker = new JPAUserBroker();
        try {
            return broker.getById(id);
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
