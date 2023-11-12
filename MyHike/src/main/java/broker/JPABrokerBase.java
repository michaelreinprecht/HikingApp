package broker;

import myHikeJava.ResourceServlet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
        insert(value);
    }

    public void delete(T value) throws SQLException {
        EntityManager entityManager = ResourceServlet.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(value);
        entityManager.getTransaction().commit();
    }
}
