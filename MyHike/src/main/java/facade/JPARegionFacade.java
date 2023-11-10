package facade;

import broker.JPABrokerBase;
import broker.JPARegionBroker;
import models.Region;

import java.sql.SQLException;
import java.util.List;

public class JPARegionFacade extends JPAFacade {
    public List<Region> getAllRegions() {
        JPABrokerBase<Region> broker = new JPARegionBroker();
        try {
            return broker.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Region getRegionById(String id) {
        JPABrokerBase<Region> broker = new JPARegionBroker();
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
