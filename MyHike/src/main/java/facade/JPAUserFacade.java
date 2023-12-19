package facade;

import broker.JPABrokerBase;
import broker.JPAUserBroker;
import models.User;

import java.sql.SQLException;
import java.util.List;

public class JPAUserFacade extends JPAFacade {
    public List<User> getAllUsers() throws SQLException, NullPointerException {
        JPABrokerBase<User> broker = new JPAUserBroker();
        return broker.getAll();
    }

    public User getUserById(String id) throws SQLException, NullPointerException {
        JPABrokerBase<User> broker = new JPAUserBroker();
        return broker.getById(id);
    }
}
