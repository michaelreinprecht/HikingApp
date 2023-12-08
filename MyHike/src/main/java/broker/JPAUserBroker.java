package broker;

import models.User;
import servlets.ResourceServlet;

import javax.persistence.EntityManager;
import java.util.List;

public class JPAUserBroker extends JPABrokerBase<User> {
    @Override
    public List<User> getAll() {
        EntityManager entityManager = ResourceServlet.getEntityManager();
        List<User> users;
        users = entityManager.createQuery("from models.User", User.class).getResultList();
        return users;
    }

    @Override
    public User getById(Object id) {
        EntityManager entityManager = ResourceServlet.getEntityManager();
        return entityManager.find(User.class, id);
    }
}
