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
        HttpServletRequest mockedRequest = getMockedRequest();
        HttpServletResponse mockedResponse = getMockedResponse();

        //Case: user is admin
        Hike oldHike = getExpectedHike();
        boolean userIsAuthorized = softDeleteHikeServlet.handleAuthForHike(oldHike ,mockedRequest, mockedResponse);
        assertTrue(userIsAuthorized);

        //Case: user is no admin and doesn't own hike
        HttpSession mockSession = mock(HttpSession.class);
        when(mockedRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("isAdmin")).thenReturn(false);
        when(mockSession.getAttribute("username")).thenReturn("noOwner");
        userIsAuthorized = softDeleteHikeServlet.handleAuthForHike(oldHike ,mockedRequest, mockedResponse);
        assertFalse(userIsAuthorized);

        //Case: user is not admin but owns the hike
        when(mockSession.getAttribute("username")).thenReturn("admin");
        userIsAuthorized = softDeleteHikeServlet.handleAuthForHike(oldHike ,mockedRequest, mockedResponse);
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