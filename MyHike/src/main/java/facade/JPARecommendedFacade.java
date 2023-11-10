package facade;

import broker.JPABrokerBase;
import broker.JPARecommendedBroker;
import models.Recommended;

import java.sql.SQLException;
import java.util.List;

public class JPARecommendedFacade extends JPAFacade {
    public List<Recommended> getAllRecommendeds() {
        JPABrokerBase<Recommended> broker = new JPARecommendedBroker();
        try {
            return broker.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Recommended getRecommendedById(int id) {
        JPABrokerBase<Recommended> broker = new JPARecommendedBroker();
        try {
            return broker.getById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
