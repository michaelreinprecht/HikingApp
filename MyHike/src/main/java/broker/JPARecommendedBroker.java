package broker;

import models.Hike;
import models.Month;
import models.Recommended;
import myHikeJava.ResourceServlet;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;

public class JPARecommendedBroker extends JPABrokerBase<Recommended>{
    @SuppressWarnings("unchecked")
    @Override
    public List<Recommended> getAll() throws SQLException {
        EntityManager entityManager = ResourceServlet.getEntityManager();
        List<Recommended> recommendedMonths = (List<Recommended>) entityManager.createQuery("from models.Recommended").getResultList();
        return recommendedMonths;
    }

    @Override
    public Recommended getById(Object id) throws SQLException {
        EntityManager entityManager = ResourceServlet.getEntityManager();
        return entityManager.find(Recommended.class, id);
    }
}