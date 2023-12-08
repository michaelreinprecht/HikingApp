package broker;

import models.Comment;
import servlets.ResourceServlet;

import javax.persistence.EntityManager;
import java.util.List;

public class JPACommentBroker extends JPABrokerBase<Comment> {
    @Override
    public List<Comment> getAll() {
        EntityManager entityManager = ResourceServlet.getEntityManager();
        List<Comment> comments;
        comments = entityManager.createQuery("from models.Comment", Comment.class).getResultList();
        return comments;
    }

    @Override
    public Comment getById(Object id) {
        EntityManager entityManager = ResourceServlet.getEntityManager();
        return entityManager.find(Comment.class, id);
    }
}
