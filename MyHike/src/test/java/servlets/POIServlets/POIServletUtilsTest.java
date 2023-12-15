package servlets.POIServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Hike;
import org.junit.jupiter.api.Test;
import servlets.TestHelper;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class POIServletUtilsTest extends TestHelper {
    POIServletUtils poiServletUtils = new POIServletUtils();
    @Test
    void handleAuthForHike() throws ServletException, IOException {
        HttpServletRequest mockedRequest = getMockedRequest();
        HttpServletResponse mockedResponse = getMockedResponse();

        //Case: user is admin
        Hike oldHike = getExpectedHike();
        boolean userIsAuthorized = poiServletUtils.handleAuthForHike(oldHike ,mockedRequest, mockedResponse);
        assertTrue(userIsAuthorized);

        //Case: user is no admin and doesn't own hike
        HttpSession mockSession = mock(HttpSession.class);
        when(mockedRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("isAdmin")).thenReturn(false);
        when(mockSession.getAttribute("username")).thenReturn("noOwner");
        userIsAuthorized = poiServletUtils.handleAuthForHike(oldHike ,mockedRequest, mockedResponse);
        assertFalse(userIsAuthorized);

        //Case: user is not admin but owns the hike
        when(mockSession.getAttribute("username")).thenReturn("admin");
        userIsAuthorized = poiServletUtils.handleAuthForHike(oldHike ,mockedRequest, mockedResponse);
        assertTrue(userIsAuthorized);
    }
}