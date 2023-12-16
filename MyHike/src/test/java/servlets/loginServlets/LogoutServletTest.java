package servlets.loginServlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import servlets.TestHelper;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LogoutServletTest extends TestHelper {
    private final LogoutServlet logoutServlet = new LogoutServlet();
    @Test
    void logoutUser() throws ServletException, IOException {
        HttpServletRequest request = getMockedRequest();
        HttpServletResponse response = getMockedResponse();

        logoutServlet.logoutUser(request, response);
        assertTrue(logoutServlet.getError().isEmpty());
    }

    @Override
    protected HttpServletRequest getMockedRequest() throws ServletException, IOException {
        HttpServletRequest request = super.getMockedRequest();
        when(request.getRequestDispatcher(any(String.class))).thenReturn(mock(RequestDispatcher.class));
        return request;
    }
}