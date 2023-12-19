package facade;

import broker.*;
import models.*;

import java.sql.SQLException;

@SuppressWarnings({"unchecked", "rawtypes", "CallToPrintStackTrace"})
public class JPAFacade implements IDatabaseFacade {
    public void insert(Object databaseObject) throws SQLException ,NullPointerException {
        JPABrokerBase broker = getBroker(databaseObject);
        broker.insert(databaseObject);
    }

    public void update(Object databaseObject) throws SQLException ,NullPointerException {
        JPABrokerBase broker = getBroker(databaseObject);
        broker.update(databaseObject);
    }

    public void delete(Object databaseObject) throws SQLException ,NullPointerException {
        JPABrokerBase broker = getBroker(databaseObject);
        broker.delete(databaseObject);
    }

    private JPABrokerBase getBroker(Object databaseObject) throws NullPointerException {
        if (databaseObject instanceof Hike) {
            return new JPAHikeBroker();
        }
        if (databaseObject instanceof Region) {
            return new JPARegionBroker();
        }
        if (databaseObject instanceof PointOfInterest) {
            return new JPAPointOfInterestBroker();
        }
        if (databaseObject instanceof User) {
            return new JPAUserBroker();
        }
        if (databaseObject instanceof Comment) {
            return new JPACommentBroker();
        }
        return null;
    }
}
