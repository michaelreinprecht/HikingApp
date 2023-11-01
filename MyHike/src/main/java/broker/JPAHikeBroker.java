package broker;

import models.Hike;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;

public class JPAHikeBroker extends JPABrokerBase<Hike> {
    @SuppressWarnings("unchecked")
    @Override
    public List<Hike> getAll() throws SQLException {
        EntityManager entityManager = getEntityManager();
        List<Hike> hikes= (List<Hike>) entityManager.createQuery("from models.Hike").getResultList();
        entityManager.close();
        return hikes;
    }
}
