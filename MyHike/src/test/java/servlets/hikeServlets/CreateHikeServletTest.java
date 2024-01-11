package servlets.hikeServlets;

import database.Database;
import facade.JPAFacade;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Comment;
import models.Hike;
import models.PointOfInterest;
import org.junit.jupiter.api.Test;
import servlets.TestHelper;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
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
        hike.setHikeOfUser(getExpectedUser());
        Hike expectedHike = getExpectedHike();
        assertEquals(expectedHike.toString(), hike.toString());
    }

    @Test
    void handleAuth() throws IOException, ServletException {
        HttpServletRequest mockedRequest = getMockedRequest();
        HttpServletResponse mockedResponse = getMockedResponse();

        //Case: user is logged in
        boolean userIsAuthorized = createHikeServlet.handleAuth(mockedRequest, mockedResponse);
        assertTrue(userIsAuthorized);

        //Case: user is not logged in
        HttpSession mockSession = mock(HttpSession.class);
        when(mockedRequest.getSession()).thenReturn(mockSession);
        userIsAuthorized = createHikeServlet.handleAuth(mockedRequest, mockedResponse);
        assertFalse(userIsAuthorized);
    }

    @Override
    protected  Hike getExpectedHike() {
        Hike expectedHike = super.getExpectedHike();
        expectedHike.setHikeComments(new ArrayList<Comment>());
        expectedHike.setHikePointsOfInterest(new ArrayList<PointOfInterest>());
        return expectedHike;
    }
}