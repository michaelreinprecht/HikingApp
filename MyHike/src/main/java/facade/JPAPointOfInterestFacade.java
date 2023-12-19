package facade;

import broker.JPABrokerBase;
import broker.JPAPointOfInterestBroker;
import models.PointOfInterest;

import java.sql.SQLException;
import java.util.List;

public class JPAPointOfInterestFacade extends JPAFacade{
    public List<PointOfInterest> getAllPointsOfInterest() throws SQLException ,NullPointerException {
        JPABrokerBase<PointOfInterest> broker = new JPAPointOfInterestBroker();
        return broker.getAll();
    }

    public PointOfInterest getPointOfInterestById(Object id) throws SQLException ,NullPointerException {
        JPABrokerBase<PointOfInterest> broker = new JPAPointOfInterestBroker();
        return broker.getById(id);
    }
}
