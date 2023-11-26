package broker;

import models.PointOfInterest;
import servlets.ResourceServlet;

import javax.persistence.EntityManager;
import java.util.List;

public class JPAPointOfInterestBroker extends JPABrokerBase<PointOfInterest> {
    @SuppressWarnings("unchecked")
    @Override
    public List<PointOfInterest> getAll() {
        EntityManager entityManager = ResourceServlet.getEntityManager();
        List<PointOfInterest> pointsOfInterest;
        pointsOfInterest = (List<PointOfInterest>) entityManager.createQuery("from models.PointOfInterest").getResultList();
        return pointsOfInterest;
    }

    @Override
    public PointOfInterest getById(Object name) {
        EntityManager entityManager = ResourceServlet.getEntityManager();
        return entityManager.find(PointOfInterest.class, name);
    }
}
