package broker;

import models.Month;
import myHikeJava.ResourceServlet;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;

public class JPAMonthBroker extends JPABrokerBase<Month> {
    @SuppressWarnings("unchecked")
    @Override
    public List<Month> getAll() throws SQLException {
        EntityManager entityManager = ResourceServlet.getEntityManager();
        List<Month> months= (List<Month>) entityManager.createQuery("from models.Month").getResultList();
        return months;
    }

    @Override
    public Month getById(Object id) throws SQLException {
        EntityManager entityManager = ResourceServlet.getEntityManager();
        return entityManager.find(Month.class, id);
    }
}
