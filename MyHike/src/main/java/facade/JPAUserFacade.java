package facade;

import broker.JPABrokerBase;
import broker.JPAUserBroker;

import models.User;

public class JPAUserFacade extends JPAFacade{

    public User getUserByName(String name) {
        JPABrokerBase<User> broker = new JPAUserBroker();
        return ((JPAUserBroker) broker).getUserByName(name);

    }
}
