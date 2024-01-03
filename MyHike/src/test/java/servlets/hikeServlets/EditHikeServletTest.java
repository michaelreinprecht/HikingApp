package servlets.hikeServlets;

import database.Database;
import facade.JPAFacade;
import facade.JPAHikeFacade;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Hike;
import org.junit.jupiter.api.Test;
import servlets.TestHelper;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EditHikeServletTest extends TestHelper {
    private final EditHikeServlet editHikeServlet = new EditHikeServlet();
    @Test
    void editHike() throws ServletException, IOException, SQLException {
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
        HttpServletRequest mockedRequest = getMockedRequest();
        HttpServletResponse mockedResponse = getMockedResponse();

        //Case: user is admin
        Hike oldHike = getExpectedHike();
        boolean userIsAuthorized = editHikeServlet.handleAuthForHike(oldHike ,mockedRequest, mockedResponse);
        assertTrue(userIsAuthorized);

        //Case: user is no admin and doesn't own hike
        HttpSession mockSession = mock(HttpSession.class);
        when(mockedRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("isAdmin")).thenReturn(false);
        when(mockSession.getAttribute("username")).thenReturn("noOwner");
        userIsAuthorized = editHikeServlet.handleAuthForHike(oldHike ,mockedRequest, mockedResponse);
        assertFalse(userIsAuthorized);

        //Case: user is not admin but owns the hike
        when(mockSession.getAttribute("username")).thenReturn("admin");
        userIsAuthorized = editHikeServlet.handleAuthForHike(oldHike ,mockedRequest, mockedResponse);
        assertTrue(userIsAuthorized);
    }

    @Override
    protected HttpServletRequest getMockedRequest() throws ServletException, IOException {
        HttpServletRequest request = super.getMockedRequest();
        when(request.getParameter("Id")).thenReturn("test1");
        return request;
    }

    @Override
    protected  Hike getExpectedHike() {
        Hike expectedHike = super.getExpectedHike();
        expectedHike.setHikeComments(null);
        expectedHike.setHikePointsOfInterest(null);
        return expectedHike;
    }

    //Creates a basic hike with id="test1" for us to update.
    private Hike getHikeToEdit() {
        Hike hike = new Hike();
        hike.setHikeId("test1");
        return hike;
    }
}