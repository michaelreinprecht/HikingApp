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

    public User getUserByLogin(User user) {
        User userUser = null;

        EntityManager entityManager = ResourceServlet.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Query query = entityManager.createQuery("SELECT u FROM User u where u.userName = ?1 and u.userPassword = ?2");
            query.setParameter(1, user.getUserName()).setParameter(2, user.getUserPassword());
            userUser = (User) query.getSingleResult();
            transaction.commit();
        } catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        } finally {
            if (entityManager != null)
                entityManager.close();
        }
        return userUser;
    }
}
