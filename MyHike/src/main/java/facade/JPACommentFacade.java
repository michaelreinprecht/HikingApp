package facade;

import broker.JPABrokerBase;
import broker.JPACommentBroker;
import models.Comment;

import java.sql.SQLException;
import java.util.List;

public class JPACommentFacade extends JPAFacade {
    public List<Comment> getAllComments() {
        JPABrokerBase<Comment> broker = new JPACommentBroker();
        try {
            return broker.getAll();
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Comment getCommentById(String id) {
        JPABrokerBase<Comment> broker = new JPACommentBroker();
        try {
            return broker.getById(id);
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
