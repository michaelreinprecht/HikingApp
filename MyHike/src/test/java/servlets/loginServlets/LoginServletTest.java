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

class LoginServletTest extends TestHelper {
    private final LoginServlet loginServlet = new LoginServlet();
    @Test
    void loginUser() throws ServletException, IOException {
        HttpServletRequest request = getMockedRequest();
        HttpServletResponse response = getMockedResponse();

        loginServlet.loginUser(request, response);
        assertTrue(loginServlet.getError().isEmpty());
    }

    @Override
    protected HttpServletRequest getMockedRequest() throws ServletException, IOException {
        HttpServletRequest request = super.getMockedRequest();
        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("admin");
        when(request.getRequestDispatcher(any(String.class))).thenReturn(mock(RequestDispatcher.class));
        return request;
    }
}