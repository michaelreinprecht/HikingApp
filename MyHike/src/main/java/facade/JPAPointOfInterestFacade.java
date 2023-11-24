package facade;

import broker.JPABrokerBase;
import broker.JPAHikeBroker;
import broker.JPAPointOfInterestBroker;
import models.Hike;
import models.PointOfInterest;

import java.sql.SQLException;
import java.util.List;

@SuppressWarnings("ALL")
public class JPAPointOfInterestFacade extends JPAFacade{
    public List<PointOfInterest> getAllPointsOfInterest() {
        JPABrokerBase<PointOfInterest> broker = new JPAPointOfInterestBroker();
        try {
            return broker.getAll();
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PointOfInterest getPointOfInterestById(Object id) {
        JPABrokerBase<PointOfInterest> broker = new JPAPointOfInterestBroker();
        try {
            return broker.getById(id);
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
