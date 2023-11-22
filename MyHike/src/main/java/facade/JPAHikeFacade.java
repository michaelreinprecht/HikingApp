package facade;

import broker.JPABrokerBase;
import broker.JPAHikeBroker;
import models.Hike;

import java.sql.SQLException;
import java.util.List;

@SuppressWarnings("ALL")
public class JPAHikeFacade extends JPAFacade{
    public List<Hike> getAllHikes() {
        JPABrokerBase<Hike> broker = new JPAHikeBroker();
        try {
            return broker.getAll();
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Hike getHikeById(Object id) {
        JPABrokerBase<Hike> broker = new JPAHikeBroker();
        try {
            return broker.getById(id);
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
