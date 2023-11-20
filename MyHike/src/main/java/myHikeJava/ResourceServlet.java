package myHikeJava;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@WebListener
public class ResourceServlet implements ServletContextListener {
    private static EntityManager entityManager;
    // Called when the servlet context is initialized
    public void contextInitialized(ServletContextEvent sce) {

    }

    // Called when the servlet context is about to be destroyed
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Close entityManager when page is closed
        entityManager.close();
    }

    public static EntityManager getEntityManager() {
        //Make sure only one entityManager exists at a time.
        if (entityManager == null) {
            EntityManagerFactory fact = Persistence.createEntityManagerFactory("ftb_inv_2023_vz_3_d");
            entityManager = fact.createEntityManager();
        }
        return entityManager;
    }
}