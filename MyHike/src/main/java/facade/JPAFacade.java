package facade;

import broker.JPABrokerBase;
import broker.JPAHikeBroker;
import models.Hike;

import java.sql.SQLException;
import java.util.List;

public class JPAFacade implements IDatabaseFacade {

    public static void main(String[] args) {
        //Testen Annotations
        //Hibernate Test getById
        JPAFacade facade = new JPAFacade();
        Hike hike= facade.getHikeById(1);
        System.out.println(hike.getHikeName());
    }

    public void save(Object value) {
        JPABrokerBase broker = getBroker(value);
        try {
            broker.insert(value);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void update(Object value) {
        JPABrokerBase broker = getBroker(value);
        try {
            broker.update(value);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void delete(Object value) {
        JPABrokerBase broker = getBroker(value);
        try {
            broker.delete(value);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    public List<Hike> getAllHikes() {
        JPABrokerBase<Hike> broker = new JPAHikeBroker();
        try {
            return broker.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Hike getHikeById(int id) {
        JPABrokerBase<Hike> broker = new JPAHikeBroker();
        try {
            return broker.getById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JPABrokerBase getBroker(Object value) {
        if (value instanceof Hike) {
            return new JPAHikeBroker();
        }
        return null;
    }
}
