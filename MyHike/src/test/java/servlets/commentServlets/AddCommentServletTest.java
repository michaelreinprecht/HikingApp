package servlets.commentServlets;

import database.Database;
import facade.JPAFacade;
import facade.JPAHikeFacade;
import facade.JPAUserFacade;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Hike;
import models.User;
import org.junit.jupiter.api.Test;
import servlets.TestHelper;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AddCommentServletTest extends TestHelper {
    private final AddCommentServlet addCommentServlet = new AddCommentServlet();
    @Test
    void addComment() throws ServletException, IOException {
        //Mock database
        Database.facade = mock(JPAFacade.class);
        Database.hikeFacade = mock(JPAHikeFacade.class);
        Database.userFacade = mock(JPAUserFacade.class);

        Hike expectedHike = getExpectedHike();
        when(Database.hikeFacade.getHikeById(any(String.class))).thenReturn(expectedHike);

        User expectedUser = getExpectedUser();
        when(Database.userFacade.getUserById(any(String.class))).thenReturn(expectedUser);

        HttpServletRequest request = getMockedRequest();
        HttpServletResponse response = getMockedResponse();

        addCommentServlet.addComment(request, response);
        assertTrue(addCommentServlet.getError().isEmpty());
    }

    @Test
    void handleAuth() throws ServletException, IOException {
        HttpServletRequest request = getMockedRequest();
        HttpServletResponse response = getMockedResponse();

        //Case: user is logged in and can create comments
        boolean isAuthorized = addCommentServlet.handleAuth(request, response);
        assertTrue(isAuthorized);

        //Case: user is not logged in and can not create comments
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("username")).thenReturn(null);
        isAuthorized = addCommentServlet.handleAuth(request, response);
        assertFalse(isAuthorized);
    }

    @Override
    protected HttpServletRequest getMockedRequest() throws IOException, ServletException {
        HttpServletRequest request = super.getMockedRequest();
        when(request.getParameter("hikeId")).thenReturn("test1");
        when(request.getParameter("commentDescription")).thenReturn("Testing");
        return request;
    }
}