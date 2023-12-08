package broker;

import models.Hike;
import servlets.ResourceServlet;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

public class JPAHikeBroker extends JPABrokerBase<Hike> {
    @Override
    public List<Hike> getAll() {
        EntityManager entityManager = ResourceServlet.getEntityManager();
        List<Hike> hikes = entityManager.createQuery("from models.Hike", Hike.class).getResultList();

        List<Hike> filteredHikes;
        filteredHikes = hikes.stream().filter(hike -> !hike.getIsDeleted()).collect(Collectors.toList());

        return filteredHikes;
    }

    @Override
    public Hike getById(Object id) {
        EntityManager entityManager = ResourceServlet.getEntityManager();
        return entityManager.find(Hike.class, id);
    }
}
