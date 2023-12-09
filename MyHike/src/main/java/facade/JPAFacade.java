package facade;

import broker.*;
import models.*;

import java.sql.SQLException;

@SuppressWarnings({"unchecked", "rawtypes", "CallToPrintStackTrace"})
public class JPAFacade implements IDatabaseFacade {
    public void insert(Object databaseObject) {
        JPABrokerBase broker = getBroker(databaseObject);
        try {
            broker.insert(databaseObject);
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void update(Object databaseObject) {
        JPABrokerBase broker = getBroker(databaseObject);
        try {
            broker.update(databaseObject);
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void delete(Object databaseObject) {
        JPABrokerBase broker = getBroker(databaseObject);
        try {
            broker.delete(databaseObject);
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
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
