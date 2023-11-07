package broker;

import models.Month;
import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;

public class JPAMonthBroker extends JPABrokerBase<Month> {
    @SuppressWarnings("unchecked")
    @Override
    public List<Month> getAll() throws SQLException {
        EntityManager entityManager = getEntityManager();
        List<Month> months= (List<Month>) entityManager.createQuery("from models.Month").getResultList();
        entityManager.close();
        return months;
    }

    @Override
    public Month getById(int id) throws SQLException {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.find(Month.class, id);
        } finally {
            entityManager.close();
        }
    }
}
