package facade;

import broker.*;
import models.Hike;
import models.Region;

import java.sql.SQLException;
import java.util.Arrays;

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

    private JPABrokerBase getBroker(Object databaseObject) {
        if (databaseObject instanceof Hike) {
            return new JPAHikeBroker();
        }
        if (databaseObject instanceof Region) {
            return new JPARegionBroker();
        }
        return null;
    }
}
