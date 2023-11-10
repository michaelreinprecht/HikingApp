package broker;

import models.Month;
import models.Region;
import myHikeJava.ResourceServlet;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;

public class JPARegionBroker extends JPABrokerBase<Region> {
    @SuppressWarnings("unchecked")
    @Override
    public List<Region> getAll() throws SQLException {
        EntityManager entityManager = ResourceServlet.getEntityManager();
        List<Region> regions= (List<Region>) entityManager.createQuery("from models.Region").getResultList();
        return regions;
    }

    @Override
    public Region getById(Object name) throws SQLException {
        EntityManager entityManager = ResourceServlet.getEntityManager();
        return entityManager.find(Region.class, name);
    }
}
