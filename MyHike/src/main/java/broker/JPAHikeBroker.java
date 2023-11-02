package broker;

import models.Hike;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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

    @Override
    public Hike getById(int id) throws SQLException {
        try (Session session = getSession();) {
            Hike hike = session.get(Hike.class, id);
            return hike;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
