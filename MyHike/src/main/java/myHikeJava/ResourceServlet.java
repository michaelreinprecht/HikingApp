package myHikeJava;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;

@WebListener
public class ResourceServlet implements ServletContextListener {
    private static EntityManager entityManager;
    // Called when the servlet context is initialized
    public void contextInitialized(ServletContextEvent sce) {
        // Create global entity manager
        try {
            entityManager = getEntityManager();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Called when the servlet context is about to be destroyed
    public void contextDestroyed(ServletContextEvent sce) {
        // Close entityManager when page is closed
        entityManager.close();
    }

    public static EntityManager getEntityManager() throws SQLException {
        //Make sure only one entityManager exists at a time.
        if (entityManager == null) {
            EntityManagerFactory fact = Persistence.createEntityManagerFactory("ftb_inv_2023_vz_3_d");
            entityManager = fact.createEntityManager();
        }
        return entityManager;
    }
}