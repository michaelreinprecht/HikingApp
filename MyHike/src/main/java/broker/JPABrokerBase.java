package broker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.List;

public abstract class JPABrokerBase<T> {
    public EntityManager getEntityManager() throws SQLException{
        EntityManagerFactory fact = Persistence.createEntityManagerFactory("MyHike");
        EntityManager entityManager = fact.createEntityManager();
        return entityManager;
    }

    public abstract List<T> getAll() throws SQLException;

    public void insert(T value) throws SQLException {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(value);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public void update(T value) throws SQLException {
        insert(value);
    }

    public void delete(T value) throws SQLException {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(value);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
}
