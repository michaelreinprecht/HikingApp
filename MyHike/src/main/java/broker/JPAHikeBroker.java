package broker;

import models.Hike;
import myHikeJava.ResourceServlet;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;

public class JPAHikeBroker extends JPABrokerBase<Hike> {
    @SuppressWarnings("unchecked")
    @Override
    public List<Hike> getAll() throws SQLException {
        EntityManager entityManager = ResourceServlet.getEntityManager();
        List<Hike> hikes= (List<Hike>) entityManager.createQuery("from models.Hike").getResultList();
        return hikes;
    }

    @Override
    public Hike getById(Object id) throws SQLException {
        EntityManager entityManager = ResourceServlet.getEntityManager();
        return entityManager.find(Hike.class, id);
    }
}
