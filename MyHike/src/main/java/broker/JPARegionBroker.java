package broker;

import models.Region;
import servlets.ResourceServlet;

import javax.persistence.EntityManager;
import java.util.List;

public class JPARegionBroker extends JPABrokerBase<Region> {
    @SuppressWarnings("unchecked")
    @Override
    public List<Region> getAll() {
        EntityManager entityManager = ResourceServlet.getEntityManager();
        List<Region> regions;
        regions = (List<Region>) entityManager.createQuery("from models.Region").getResultList();
        return regions;
    }

    @Override
    public Region getById(Object name) {
        EntityManager entityManager = ResourceServlet.getEntityManager();
        return entityManager.find(Region.class, name);
    }
}
