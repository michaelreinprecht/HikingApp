package facade;

import broker.JPABrokerBase;
import broker.JPAMonthBroker;
import models.Month;

import java.sql.SQLException;
import java.util.List;

public class JPAMonthFacade extends JPAFacade {
    public List<Month> getAllMonths() {
        JPABrokerBase<Month> broker = new JPAMonthBroker();
        try {
            return broker.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Month getMonthById(int id) {
        JPABrokerBase<Month> broker = new JPAMonthBroker();
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
