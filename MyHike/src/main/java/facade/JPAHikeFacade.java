package facade;

import broker.JPABrokerBase;
import broker.JPAHikeBroker;
import models.Hike;

import java.sql.SQLException;
import java.util.List;


public class JPAHikeFacade extends JPAFacade{
    public List<Hike> getAllHikes() throws SQLException ,NullPointerException {
        JPABrokerBase<Hike> broker = new JPAHikeBroker();
        return broker.getAll();
    }

    public List<Hike> getHikeByUser(String username) throws SQLException ,NullPointerException{
        JPAHikeBroker broker = new JPAHikeBroker();
        return broker.getByUser(username);
    }

    public Hike getHikeById(Object id) throws SQLException ,NullPointerException {
        JPABrokerBase<Hike> broker = new JPAHikeBroker();
        return broker.getById(id);
    }
}
