package servlets.commentServlets;

import database.Database;
import facade.JPACommentFacade;
import facade.JPAFacade;
import facade.JPAHikeFacade;
import facade.JPAUserFacade;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Comment;
import models.Hike;
import models.User;
import org.junit.jupiter.api.Test;
import servlets.TestHelper;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeleteCommentServletTest extends TestHelper {
    private final DeleteCommentServlet deleteCommentServlet = new DeleteCommentServlet();
    @Test
    void deleteComment() throws IOException, ServletException {
        //Mock database
        Database.facade = mock(JPAFacade.class);
        Database.hikeFacade = mock(JPAHikeFacade.class);
        Database.commentFacade = mock(JPACommentFacade.class);

        Hike expectedHike = getExpectedHike();
        when(Database.hikeFacade.getHikeById(any(String.class))).thenReturn(expectedHike);

        Comment expectedComment = getExpectedComment();
        when(Database.commentFacade.getCommentById(any(String.class))).thenReturn(expectedComment);

        HttpServletRequest request = getMockedRequest();
        HttpServletResponse response = getMockedResponse();

        deleteCommentServlet.deleteComment(request, response);
        assertTrue(deleteCommentServlet.getError().isEmpty());
    }

    @Test
    void handleAuthForComment() throws ServletException, IOException {
        Comment expectedComment = getExpectedComment();

        //Mock request and response
        HttpServletRequest request = getMockedRequest();
        HttpServletResponse response = getMockedResponse();

        //Case: User is admin and can delete any comment.
        boolean isAuthorized = deleteCommentServlet.handleAuthForComment(expectedComment, request, response);
        assertTrue(isAuthorized);

        //Case: user is no admin and doesn't own comment.
        HttpSession mockSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("isAdmin")).thenReturn(false);
        when(mockSession.getAttribute("username")).thenReturn("noOwner");
        isAuthorized = deleteCommentServlet.handleAuthForComment(expectedComment, request, response);
        assertFalse(isAuthorized);

        //Case: user is not admin but owns the hike
        when(mockSession.getAttribute("username")).thenReturn("admin");
        isAuthorized = deleteCommentServlet.handleAuthForComment(expectedComment, request, response);
        assertTrue(isAuthorized);
    }

    @Override
    protected HttpServletRequest getMockedRequest() throws IOException, ServletException {
        HttpServletRequest request = super.getMockedRequest();
        when(request.getParameter("hikeId")).thenReturn("test1");
        when(request.getParameter("commentId")).thenReturn("commentTest1");
        return request;
    }
}