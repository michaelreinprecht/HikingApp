package broker;

import models.Hike;
import myHikeJava.ResourceServlet;
import org.hibernate.Criteria;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class JPAHikeBroker extends JPABrokerBase<Hike> {
    @SuppressWarnings("unchecked")
    @Override
    public List<Hike> getAll() throws SQLException {
        EntityManager entityManager = ResourceServlet.getEntityManager();
        List<Hike> hikes = entityManager.createQuery("from models.Hike", Hike.class).getResultList();

        List<Hike> filteredHikes = hikes.stream().filter(hike -> !hike.getIsDeleted()).collect(Collectors.toList());

        //List<Hike> hikes = entityManager.createQuery("select h from models.Hike h where h.isDeleted = false", Hike.class).getResultList();

        return filteredHikes;
    }

    @Override
    public Hike getById(Object id) throws SQLException {
        EntityManager entityManager = ResourceServlet.getEntityManager();
        return entityManager.find(Hike.class, id);
    }
}
