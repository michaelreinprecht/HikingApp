package facade;

import broker.JPABrokerBase;
import broker.JPARegionBroker;
import models.Region;

import java.sql.SQLException;
import java.util.List;

public class JPARegionFacade extends JPAFacade {
    public List<Region> getAllRegions() throws SQLException ,NullPointerException {
        JPABrokerBase<Region> broker = new JPARegionBroker();
        return broker.getAll();
    }

    public Region getRegionById(String id) throws SQLException ,NullPointerException {
        JPABrokerBase<Region> broker = new JPARegionBroker();
        return broker.getById(id);
    }
}
