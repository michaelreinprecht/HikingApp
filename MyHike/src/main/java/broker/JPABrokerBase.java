package broker;

import myHikeJava.ResourceServlet;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;

public abstract class JPABrokerBase<T> {

    public abstract List<T> getAll() throws SQLException;
    public abstract T getById(Object id) throws SQLException;

    public void insert(T value) throws SQLException {
        EntityManager entityManager = ResourceServlet.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(value);
        entityManager.getTransaction().commit();
    }

    public void update(T value) throws SQLException {
        EntityManager entityManager = ResourceServlet.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(value);
        entityManager.getTransaction().commit();
    }

    public void delete(T value) throws SQLException {
        EntityManager entityManager = ResourceServlet.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(value);
        entityManager.getTransaction().commit();
    }
}
