package broker;

import models.User;
import servlets.ResourceServlet;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;

public class JPAUserBroker extends JPABrokerBase<User> {
    @Override
    public List<User> getAll() throws SQLException {
        EntityManager entityManager = ResourceServlet.getEntityManager();
        List<User> users;
        users = entityManager.createQuery("from models.User", User.class).getResultList();
        return users;
    }

    @Override
    public User getById(Object id) throws SQLException {
        EntityManager entityManager = ResourceServlet.getEntityManager();
        return entityManager.find(User.class, id);
    }
}
