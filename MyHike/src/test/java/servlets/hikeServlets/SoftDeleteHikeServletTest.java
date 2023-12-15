package servlets.hikeServlets;

import database.Database;
import facade.JPAFacade;
import facade.JPAHikeFacade;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import models.Hike;
import org.junit.jupiter.api.Test;
import servlets.TestHelper;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SoftDeleteHikeServletTest extends TestHelper {
    SoftDeleteHikeServlet softDeleteHikeServlet = new SoftDeleteHikeServlet();
    @Test
    void softDeleteHike() throws ServletException, IOException {
        Hike hike = getHikeToDelete();

        //Mock Database
        Database.facade = mock(JPAFacade.class);
        Database.hikeFacade = mock(JPAHikeFacade.class);
        when(Database.hikeFacade.getHikeById(any(String.class))).thenReturn(hike);

        softDeleteHikeServlet.softDeleteHike(getMockedRequest(), getMockedResponse());
        assertTrue(softDeleteHikeServlet.getError().isEmpty());
    }

    @Test
    void handleAuthForHike() throws ServletException, IOException {
        Hike hike = getHikeToDelete();
        boolean userIsAuthorized = softDeleteHikeServlet.handleAuthForHike(hike ,getMockedRequest(), getMockedResponse());
        assertTrue(userIsAuthorized);
    }

    //Creates a basic hike with id="test1" for us to delete.
    private Hike getHikeToDelete() {
        Hike hike = new Hike();
        hike.setHikeId("test1");
        return hike;
    }

    @Override
    protected HttpServletRequest getMockedRequest() throws ServletException, IOException {
        HttpServletRequest request = super.getMockedRequest();
        when(request.getParameter("Id")).thenReturn("test1");
        return request;
    }
}