package servlets.hikeServlets;

import database.Database;
import facade.JPAFacade;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import models.Hike;
import models.Region;
import models.User;
import org.junit.jupiter.api.Test;
import servlets.TestHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateHikeServletTest extends TestHelper {
    CreateHikeServlet createHikeServlet = new CreateHikeServlet();

    @Test
    void createHike() throws IOException, ServletException {
        Database.facade = mock(JPAFacade.class);
        createHikeServlet.createHike(getMockedRequest(), getMockedResponse());
        assertTrue(createHikeServlet.getError().isEmpty());
    }

    @Test
    void getHike() throws ServletException, IOException {
        Hike hike = createHikeServlet.getHike(getMockedRequest());
        hike.setHikeId("test1");
        Hike expectedHike = getExpectedHike();
        assertEquals(expectedHike.toString(), hike.toString());
    }

    @Test
    void handleAuth() throws IOException, ServletException {
        boolean userIsAuthorized = createHikeServlet.handleAuth(getMockedRequest(), getMockedResponse());
        assertTrue(userIsAuthorized);
    }
}