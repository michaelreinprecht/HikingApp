package broker;

import models.User;
import servlets.ResourceServlet;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class JPAUserBroker extends JPABrokerBase<User>{

    @Override
    public List<User> getAll() {
        EntityManager entityManager = ResourceServlet.getEntityManager();
        List<User> users = entityManager.createQuery("from models.User", User.class).getResultList();

        return users;
    }

    @Override
    public User getById(Object id) {
        EntityManager entityManager = ResourceServlet.getEntityManager();
        return entityManager.find(User.class, id);
    }

    public User getUserByName(String name) {
        User userUser = null;

        EntityManager entityManager = ResourceServlet.getEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM User u where u.userName = ?1");
            query.setParameter(1, name);
            userUser = (User) query.getSingleResult();
        } catch (Exception e){
            e.printStackTrace();
        }
        return userUser;
    }
}
