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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

class EditHikeServletTest extends TestHelper {
    private final EditHikeServlet editHikeServlet = new EditHikeServlet();
    @Test
    void editHike() throws ServletException, IOException {
        Hike oldHike = getHikeToEdit();

        //Mock Database
        Database.facade = mock(JPAFacade.class);
        Database.hikeFacade = mock(JPAHikeFacade.class);
        when(Database.hikeFacade.getHikeById(any(String.class))).thenReturn(oldHike);

        editHikeServlet.editHike(getMockedRequest(), getMockedResponse());
        assertTrue(editHikeServlet.getError().isEmpty());
    }

    @Test
    void getUpdatedHike() throws ServletException, IOException {
        Hike oldHike = getHikeToEdit();
        Hike hike = editHikeServlet.getUpdatedHike(oldHike, getMockedRequest());
        hike.setHikeId("test1");
        Hike expectedHike = getExpectedHike();
        assertEquals(expectedHike.toString(), hike.toString());
    }

    @Test
    void handleAuthForHike() throws IOException, ServletException {
        Hike oldHike = getHikeToEdit();
        boolean userIsAuthorized = editHikeServlet.handleAuthForHike(oldHike ,getMockedRequest(), getMockedResponse());
        assertTrue(userIsAuthorized);
    }

    @Override
    protected HttpServletRequest getMockedRequest() throws ServletException, IOException {
        HttpServletRequest request = super.getMockedRequest();
        when(request.getParameter("Id")).thenReturn("test1");
        return request;
    }

    //Creates a basic hike with id="test1" for us to update.
    private Hike getHikeToEdit() {
        Hike hike = new Hike();
        hike.setHikeId("test1");
        return hike;
    }
}