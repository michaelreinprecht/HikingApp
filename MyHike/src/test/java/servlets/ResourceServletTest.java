package servlets;

import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.*;

class ResourceServletTest {

    @Test
    void getEntityManager() {
        ResourceServlet.getEntityManager();
    }
}