package facade;

import broker.JPABrokerBase;
import broker.JPACommentBroker;
import models.Comment;

import java.sql.SQLException;
import java.util.List;

public class JPACommentFacade extends JPAFacade {
    public List<Comment> getAllComments() throws SQLException ,NullPointerException{
        JPABrokerBase<Comment> broker = new JPACommentBroker();
        return broker.getAll();
    }

    public Comment getCommentById(String id) throws SQLException ,NullPointerException {
        JPABrokerBase<Comment> broker = new JPACommentBroker();
        return broker.getById(id);
    }
}
