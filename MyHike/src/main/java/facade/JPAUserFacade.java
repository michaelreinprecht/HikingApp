package facade;

import broker.JPABrokerBase;
import broker.JPAUserBroker;

import models.User;

public class JPAUserFacade extends JPAFacade{

    public User getUserByLogin(User user) {
        JPABrokerBase<User> broker = new JPAUserBroker();
        return ((JPAUserBroker) broker).getUserByLogin(user);

    }
}
